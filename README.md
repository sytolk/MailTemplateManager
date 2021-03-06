# MailTemplateManager

template manager for javax mail. Currently send emails using Gmail SMTP - it`s need to set your user and pass in mail_config.properties (move src/test/resources/mail_config.properties file in your app resource folder)

## Usage 
replace massage body with parameters
```java
public class EmailTemplates {
	private Integer   id;
	private String    templateName;
	private String    contenttype;
	private String    emailRecipient;
	private String    mailSender;
	private String    senderName;
	private String    mailSubject;
	private String    mailBody = "Hi ${NAME} you are ${DESC} ..";
	private Boolean   recordstatus;
	private Timestamp datecreated;
	private Timestamp datemodified;
}

ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Thread(() -> {
                String name = "STANIMIR";
                String desc = "DEVELOPER";
                    try {
                        MailContainer mailContainer = new MailContainer();
                        mailContainer.addToRecipient(template.getEmailRecipient());
                        mailContainer.setSender(template.getMailSender());
                        mailContainer.setSubject(template.getMailSubject());
                        mailContainer.setContentType(template.getContenttype());

                        Map<String, String> parameterMap = new HashMap<>();
                        parameterMap.put("NAME", name);
                        parameterMap.put("DESC", desc);

                        mailContainer.setBody(MailTemplateManager.applyTemplate(template.getMailBody(), parameterMap));

                        MailManager mailManager = new MailManager(mailContainer);

                        mailManager.sendMail();
                    } catch (Exception e) {
                        LOG.error(e);
                    }
            }));
```

##Maven
```xml
<repositories>
    <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
            <groupId>com.github.sytolk</groupId>
            <artifactId>MailTemplateManager</artifactId>
            <version>1.0.3</version>
    </dependency>
</dependencies>
```