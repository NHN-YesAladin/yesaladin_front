package shop.yesaladin.front.file.service.inter;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;
import shop.yesaladin.front.file.dto.FileUploadResponseDto;

public interface FileStorageService {

    FileUploadResponseDto fileUpload(String domainName, String typeName, MultipartFile file)
            throws IOException;
}
