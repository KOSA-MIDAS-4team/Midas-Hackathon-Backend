package kosamidas.hackathon.global.generic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private T data;
    private int count;
}