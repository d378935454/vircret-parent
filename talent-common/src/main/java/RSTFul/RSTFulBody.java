package RSTFul;

import java.io.Serializable;


/**
 * Created by bean on 2016/6/15.
 */
public class RSTFulBody implements Serializable {
    private int status = 0;//成功1 失败0
    private Object body = null;//反回json对象
    private String data = null;//返回备注信息

    /**
     * 成功
     */
    public RSTFulBody success() {
        this.status = 1;
        return this;
    }

    /**
     * 成功放入对象
     */
    public RSTFulBody success(Object body) {
        this.status = 1;
        this.body = body;
        return this;
    }

    /**
     * 失败
     */
    public RSTFulBody fail() {
        this.status = 0;
        return this;
    }

    /**
     * 失败放入对象
     */
    public RSTFulBody fail(Object body) {
        this.status = 0;
        this.body = body;
        return this;
    }

    /**
     * 放入对象
     *
     * @param body
     */
    public RSTFulBody body(Object body) {
        this.body = body;
        return this;
    }

    /**
     * 放入对象
     *
     * @param data
     */
    public RSTFulBody data(String data) {
        this.data = data;
        return this;
    }

    public RSTFulBody() {
    }

    public int getStatus() {
        return status;
    }

    public Object getBody() {
        return body;
    }

    public String getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

