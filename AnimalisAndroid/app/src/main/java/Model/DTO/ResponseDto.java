package Model.DTO;

/**
 * Created by arnauddupeyrat on 18/07/16.
 */
public class ResponseDto {
    private boolean success;
    private String error;


    public ResponseDto() {
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    @Override
    public String toString() {
        return "{" +
                "success :" + success +
                ", \"error\" : \" " +  error + "\"" +
                '}';

    }
}
