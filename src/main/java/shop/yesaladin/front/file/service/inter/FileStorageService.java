package shop.yesaladin.front.file.service.inter;

import org.springframework.web.multipart.MultipartFile;
import shop.yesaladin.front.file.dto.FileUploadResponseDto;

import java.io.IOException;

/**
 * 파일 업로드/다운로드를 요청하는 Service Interface 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface FileStorageService {

    /**
     * shop에 파일 업로드를 요청하여 응답받은 Dto를 반환합니다.
     *
     * @param domainName 파일을 저장할 컨테이너 내의 도메인 경로
     * @param typeName   파일을 저장할 컨테이너 내의 도메인 경로
     * @param file       요청할 파일
     * @return 응답받은 파일의 url과 업로드 시간을 담은 Dto
     * @author 이수정
     * @since 1.0
     */
    FileUploadResponseDto fileUpload(String domainName, String typeName, MultipartFile file)
            throws IOException;

    /**
     * 파일을 다운로드 하기위해 StorageAuthService에서 토큰을 발급받고,
     * api를 호출하여 데이터를 바이트 배열로 받아 InputStream으로 반환합니다.
     *
     * @param url 파일을 다운받기 위한 파일의 url
     * @author 김홍대
     * @author 이수정
     * @since 1.0
     */
    byte[] fileDownload(String url);
}
