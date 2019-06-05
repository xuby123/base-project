package com.joe.exception;

/**
 * <dl>
 * <dt>CustomException</dt>
 * <dd>Description: 自定义异常</dd>
 * <dd>Copyright: Copyright (C) 2019</dd>
 * <dd>Company:</dd>
 * <dd>CreateDate: 2019-06-05</dd>
 * </dl>
 *
 * @author xby
 */
public class CustomException extends RuntimeException {


    public CustomException(Object Obj) {
        super(Obj.toString());
    }

}