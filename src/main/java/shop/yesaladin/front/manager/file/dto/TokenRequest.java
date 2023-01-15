package shop.yesaladin.front.manager.file.dto;

import lombok.Data;

@Data
public class TokenRequest {

    private Auth auth = new Auth();

    @Data
    public class Auth {

        private String tenantId;
        private PasswordCredentials passwordCredentials = new PasswordCredentials();
    }

    @Data
    public class PasswordCredentials {

        private String username;
        private String password;
    }
}
