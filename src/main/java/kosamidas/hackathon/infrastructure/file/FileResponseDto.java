package kosamidas.hackathon.infrastructure.file;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileResponseDto {

    private final String imgPath;
    private final String imgUrl;

    @Builder
    public FileResponseDto(String imgPath, String imgUrl) {
        this.imgPath = imgPath;
        this.imgUrl = imgUrl;
    }
}