package shop.mtcoding.securityapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO<T> {
    private Integer status;
    private String msg;
    // 오류의 디테일이 들어감, delete 빼고 다 포함
    private T data;

    // 디폴트
    // 일반적으론 아래 Dto만 return
    public ResponseDTO() {
        this.status = 200;
        this.msg = "성공";
    }

    public ResponseDTO<?> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseDTO<?> fail(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;

        return this;
    }
}
