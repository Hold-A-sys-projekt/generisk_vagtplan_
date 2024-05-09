package dat.route;

import dat.dto.EmailDTO;
import dat.util.EmailSender;
import io.javalin.apibuilder.EndpointGroup;

import java.util.List;
import java.util.Objects;

import static io.javalin.apibuilder.ApiBuilder.post;

public class EmailRoutes implements Route {

    @Override
    public String getBasePath() {
        return "/email";
    }

    @Override
    public EndpointGroup getRoutes()
    {
        return () -> {
            post("/send", ctx -> {
                EmailDTO emailDTO = ctx.bodyAsClass(EmailDTO.class);

                if (Objects.isNull(emailDTO)) {
                    ctx.status(400);
                    ctx.result("Missing parameters");
                    return;
                }

                // Send email
                EmailSender.sendEmail(emailDTO.receiver(),
                        emailDTO.subject(), List.of(emailDTO.message()), true);
                ctx.status(200);
                ctx.result("Email sent");
            });
        };
    }
}