package dat.controller;

import dat.dao.SwapRequestsDAO;
import dat.dao.SwapShiftsDAO;
import dat.dto.SwapRequestsDTO;
import dat.model.SwapRequests;
import dat.model.SwapShifts;
import dat.model.User;
import dat.util.EmailSender;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
            SwapRequests request = dto.toEntity();
            dao.create(request);

            // Generate the request URL
            String requestUrl = "http://localhost:7070/api/swaprequests/" + request.getId();

            // Send an email to the requested user
            User requestedUser = request.getShift2().getUser();
            String email = requestedUser.getEmail();
            String subject = "Swap Shift Request";
            List<String> messages = List.of(
                    "You have received a new shift swap request.",
                    "Click the link below to view and respond to the request:",
                    requestUrl
            );
            EmailSender.sendEmail(email, subject, messages, false);

            // Respond with the created request details
            ctx.status(201).json(new SwapRequestsDTO(request).setUrl(requestUrl));
        } catch (Exception e) {
            ctx.status(500).result("Internal Server Error");
        }
    }

    public void acceptRequest(Context ctx) {
        try {
            int requestId = Integer.parseInt(ctx.pathParam("id"));
            boolean isAccepted = Boolean.parseBoolean(ctx.queryParam("accepted"));

            ((SwapRequestsDAO) dao).updateRequestAcceptance(requestId, isAccepted ? "Approved" : "Not Approved");

            if (isAccepted) {
                SwapRequests request = ((SwapRequestsDAO) dao).findById(requestId);
                SwapShifts swapShift = new SwapShifts();
                swapShift.setRequest(request);
                swapShift.setIsAccepted("Pending");
                swapShiftsDAO.createSwap(swapShift);
            }
            ctx.status(204);
        } catch (Exception e) {
            ctx.status(500).result("Internal Server Error");
        }
    }

    public void getRequestsById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            SwapRequests request = ((SwapRequestsDAO) dao).findById(id);
            if (request != null) {
                ctx.json(new SwapRequestsDTO(request));
            } else {
                ctx.status(404).result("Not Found");
            }
        } catch (Exception e) {
            ctx.status(500).result("Internal Server Error");
        }
    }
}
