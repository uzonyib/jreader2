package jreader2.app;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello(OAuth2AuthenticationToken auth) {
        String email = auth.getPrincipal().getAttribute("email");
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Key key = datastore.newKeyFactory().setKind("User").newKey(email);
        Entity entity = datastore.get(key);
        if (entity != null) {
            return "Hello, " + email + "!";
        } else {
            return "Forbidden";
        }
    }

}
