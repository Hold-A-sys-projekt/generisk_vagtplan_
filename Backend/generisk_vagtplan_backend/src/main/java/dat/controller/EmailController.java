package dat.controller;

import dat.dto.EmailDTO;
import dat.util.EmailSender;
import io.javalin.http.Context;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class EmailController {
    public void sendEMail(Context ctx) {
        final EmailDTO emailDTO = ctx.bodyAsClass(EmailDTO.class);

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
    }
}