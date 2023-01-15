package shop.yesaladin.front.manager.file.service.inter;

import org.json.simple.parser.ParseException;

public interface StorageAuthService {

    String getAuthToken() throws ParseException;
}
