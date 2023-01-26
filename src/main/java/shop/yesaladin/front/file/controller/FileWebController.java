package shop.yesaladin.front.file.controller;

import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shop.yesaladin.front.file.dto.FileUploadResponseDto;
import shop.yesaladin.front.file.service.inter.FileStorageService;

/**
 * 파일 관련 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileWebController {

    private final FileStorageService fileStorageService;

    /**
     * tui-editor에서 파일 업로드를 요청하면 파일을 등록하고 등록한 파일의 url을 반환합니다.
     *
     * @param request    tui-editor에서 요청한 파일을 담고 있는 MultipartHttpServletRequest
     * @param domainName 파일을 저장할 도메인 명
     * @param typeName   파일을 저장할 파일 타입 명
     * @return 등록한 파일의 url
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/file-upload/{domainName}/{typeName}")
    public ResponseEntity fileUpload(
            MultipartHttpServletRequest request,
            @PathVariable String domainName,
            @PathVariable String typeName
    ) throws IOException {
        MultipartFile file = request.getFile("data");

        FileUploadResponseDto responseDto = fileStorageService.fileUpload(
                domainName,
                typeName,
                file
        );

        return ResponseEntity.ok().body(Map.of("url", responseDto.getUrl()));
    }

}
