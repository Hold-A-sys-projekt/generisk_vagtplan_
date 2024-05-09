### Seting up Gmail SMTP

1. Go to [Google Account](https://myaccount.google.com/) use or create a new account.
2. Select **Security**.
3. Under **"How you sign in to Google"** make sure 2-Step Verification is turned on.
4. Search for **"App passwords"** and create a new app password.
5. Follow the code exsample below [Exsample](#exsample-of-gmail-smtp).
6. Try to send an email to yourself (by runing Main.java).
7. If error occurs, check if your anitvirus, firewall or other security software is blocking the connection of port 465 (SSL) / 587 (TLS).

### Exsample of Gmail SMTP

#### Main.java / test:

```java
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        EmailSender.sendEmail("generiskvagtplan@gmail.com",
                "receiver@gmail.com",
                "Email Subject",
                List.of("Hallo, ", "World!"),
                true);
    }
}
```

#### EmailSender.java:

```java
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmailSender
{
    private static final Properties PROPERTIES = new Properties();
    private static final String USERNAME = "generiskvagtplan@gmail.com";    
    private static final String PASSWORD = Env.PASS;                        

    static
    {   // Using TLS
        PROPERTIES.put("mail.smtp.host", "smtp.gmail.com");
        PROPERTIES.put("mail.smtp.port", "587");
        PROPERTIES.put("mail.smtp.auth", "true");
        PROPERTIES.put("mail.smtp.starttls.enable", "true");
    }

    public static void sendEmail(String from, String to, String subject, List<String> messages, boolean debug)
    {
        Authenticator authenticator = new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };

        Session session = Session.getInstance(PROPERTIES, authenticator);
        session.setDebug(debug);

        try
        {
            // Create a message with headers
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // Create message body
            Multipart mp = new MimeMultipart();
            for (String message : messages)
            {
                MimeBodyPart mbp = new MimeBodyPart();
                mbp.setText(message, "us-ascii");
                mp.addBodyPart(mbp);
            }
            msg.setContent(mp);

            // Send the message
            Transport.send(msg);
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null)
            {
                ex.printStackTrace();
            }
        }
    }
}
```

#### Env.java:
This is tmp solution, and when PASS should be sat as an environment variable on server.

```java
package dat.util;

public class Env
{
    public static final String PASS = "app password";
}
```


#### pom.xml:

```xml
<dependencies>
    <dependency>
        <groupId>jakarta.mail</groupId>
        <artifactId>jakarta.mail-api</artifactId>
        <version>2.1.2</version>
    </dependency>

    <dependency>
        <groupId>org.eclipse.angus</groupId>
        <artifactId>jakarta.mail</artifactId>
        <version>2.0.1</version>
    </dependency>
</dependencies>
```
