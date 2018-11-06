package com.cvs.mailer.resource;

import com.cvs.mailer.util.AppProperties;
import com.cvs.mailer.util.MailCreation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cvs.mailer.representation.Mailer;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@Api(value="mailOptions", description="Operations pertaining to mailing application")
@RequestMapping(path="/api/v1")
public class MailerController {

    @Autowired
    AppProperties mailProperties;

    private final AtomicLong counter = new AtomicLong();

    @ApiOperation(value = "Send the mail of inference report",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully sent the mail"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            }
    )
    @RequestMapping(value = "/email", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mailReport(@RequestParam(value="toAddress") String toAddress,
                                              @RequestParam(value="subject") String subject,
                                              @RequestParam(value="body") String body){
        long id = counter.incrementAndGet();
        Mailer mail = new Mailer(id, toAddress, subject, body);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = createMail(mail);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    private ResponseEntity<String> createMail(Mailer mail) throws IOException{
        MailCreation createMail = new MailCreation(mail, mailProperties);
        String responseMessage = createMail.prepareAndSendMail();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMessage);
    }
}
