package cn.chenc.blog.file.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class FileResponse {

    private Integer code;
    private Object data;
    private String name;
    private String msg;

    public FileResponse() {
    }

    public FileResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
