package shop.yesaladin.front.file.service.inter;

import java.io.InputStream;

public interface FileStorageService {

    String fileUpload(String token, String containerName, String fileName, final InputStream inputStream);
}
