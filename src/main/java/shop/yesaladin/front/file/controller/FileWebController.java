package shop.yesaladin.front.file.controller;

import static java.util.UUID.randomUUID;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shop.yesaladin.front.file.dto.FormDataDto;
import shop.yesaladin.front.file.service.inter.FileStorageService;
import shop.yesaladin.front.file.service.inter.StorageAuthService;

/**
 * 파일 관련 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/web/files")
public class FileWebController {

    private final FileStorageService fileStorageService;
    private final StorageAuthService storageAuthService;

    @PostMapping("/file-upload/{domainName}/{typeName}")
    public ResponseEntity fileUpload(
            MultipartHttpServletRequest request,
            @PathVariable String domainName,
            @PathVariable String typeName
    ) {
        try {
            MultipartFile file = request.getFile("data");
            String filename = randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

            String token = storageAuthService.getAuthToken();

            String url = fileStorageService.fileUpload(token, domainName + "/" + typeName, filename, file.getInputStream());

            return ResponseEntity.ok().body(Map.of("url", url));
        } catch (Exception e) {
            // TODO: 에러 보내는거 무조건 다시 만들기!! 이건 임시!!
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
