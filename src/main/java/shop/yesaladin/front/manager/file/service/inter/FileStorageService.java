package shop.yesaladin.front.manager.file.service.inter;

import java.io.InputStream;
import shop.yesaladin.front.manager.file.dto.FileUploadResponseDto;

public interface FileStorageService {

    FileUploadResponseDto fileUpload(String token, String containerName, String fileName, final InputStream inputStream);
}
