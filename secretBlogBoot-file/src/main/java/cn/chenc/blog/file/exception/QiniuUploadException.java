package cn.chenc.blog.file.exception;

public class QiniuUploadException extends FileException {
    public QiniuUploadException() {
        super();
    }

    public QiniuUploadException(String message) {
        super(message);
    }

    public QiniuUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public QiniuUploadException(Throwable cause) {
        super(cause);
    }

    protected QiniuUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
