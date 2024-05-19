package dat.controller;

import dat.dao.SwapRequestsDAO;
import dat.dao.SwapShiftsDAO;
import dat.dto.SwapRequestsDTO;
import dat.model.SwapRequests;
import dat.model.SwapShifts;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwapRequestsController extends Controller<SwapRequests, SwapRequestsDTO> {

    private static final Logger logger = LoggerFactory.getLogger(SwapRequestsController.class);
    private final SwapShiftsDAO swapShiftsDAO;

    public SwapRequestsController(SwapRequestsDAO swapRequestsDAO, SwapShiftsDAO swapShiftsDAO) {
        super(swapRequestsDAO);
        this.swapShiftsDAO = swapShiftsDAO;
    }

    public void createRequest(Context ctx) {
        try {
            SwapRequestsDTO dto = ctx.bodyAsClass(SwapRequestsDTO.class);
            logger.info("Received swap request DTO: {}", dto);
            SwapRequests request = dto.toEntity();
            dao.create(request);
            ctx.status(201).json(new SwapRequestsDTO(request));
            logger.info("Created swap request: {}", request.getId());
        } catch (Exception e) {
            logger.error("Error creating swap request", e);
            ctx.status(500).result("Internal Server Error");
        }
    }

    public void acceptRequest(Context ctx) {
        try {
            int requestId = Integer.parseInt(ctx.pathParam("id"));
            boolean isAccepted = Boolean.parseBoolean(ctx.queryParam("accepted"));
            logger.info("Processing acceptance for request ID: {} with status: {}", requestId, isAccepted);
            ((SwapRequestsDAO) dao).updateRequestAcceptance(requestId, isAccepted ? "Approved" : "Not Approved");

            if (isAccepted) {
                SwapRequests request = ((SwapRequestsDAO) dao).findById(requestId);
                SwapShifts swapShift = new SwapShifts();
                swapShift.setRequest(request);
                swapShift.setIsAccepted("Pending");
                swapShiftsDAO.createSwap(swapShift);
                logger.info("Created swap shift for request ID: {}", requestId);
            }

            ctx.status(204);
        } catch (Exception e) {
            logger.error("Error accepting swap request", e);
            ctx.status(500).result("Internal Server Error");
        }
    }
}
