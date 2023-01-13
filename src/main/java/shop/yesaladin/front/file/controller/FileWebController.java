package shop.yesaladin.front.file.controller;

import static java.util.UUID.randomUUID;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shop.yesaladin.front.file.dto.FormDataDto;

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

    private final String FILE_PATH = "/Users/sudang/works/file/";

    @PostMapping("/file-upload")
    public ResponseEntity fileUpload(MultipartHttpServletRequest request) throws IOException {
        try {
            MultipartFile file = request.getFile("data");

            File uploadDir = new File(FILE_PATH);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filename = randomUUID().toString() + "_" + file.getOriginalFilename();

            file.transferTo(Paths.get(FILE_PATH + filename));

            return ResponseEntity.ok().body(Map.of("filename", filename));
        } catch (Exception e) {
            // TODO: 에러 보내는거 무조건 다시 만들기!! 이건 임시!!
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
