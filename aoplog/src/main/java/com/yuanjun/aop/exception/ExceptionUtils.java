package com.yuanjun.aop.exception;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * �쳣������
 */
public class ExceptionUtils {

    /**
     * Build a message for the given base message and root cause.
     *
     * @param message the base message
     * @param cause   the root cause
     * @return the full exception message
     */
    public static String buildMessage(String message, Throwable cause) {
        if (cause != null) {
            StringBuilder sb = new StringBuilder();
            if (message != null) {
                sb.append(message).append("; ");
            }
            sb.append("nested exception is ").append(cause);
            return sb.toString();
        } else {
            return message;
        }
    }

    /**
     * ��ȡ�쳣��ջ��Ϣ
     *
     * @param e �쳣
     * @return �쳣��ջ��Ϣ
     */
    public static String getExcTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        e.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        writer.close();
        try {
            stringWriter.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return buffer.toString();
    }
}

