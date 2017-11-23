package com.lovecws.mumu.rpc.motan.serialize;

/**
 * 它的作用就是把对象转化为byte数组，或把byte数组转化为对象。
 */
import com.weibo.api.motan.codec.Serialization;
import com.weibo.api.motan.core.extension.Scope;
import com.weibo.api.motan.core.extension.Spi;
import com.weibo.api.motan.core.extension.SpiMeta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Spi(
        scope = Scope.SINGLETON
)
@SpiMeta(
        name = "jdk"
)
public class JDKSerialization implements Serialization {

    public byte[] serialize(Object o) {
        if(o==null){
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outo = new ObjectOutputStream(out);
            outo.writeObject(o);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    public <T> T  deserialize(byte[] b,Class<T> var2) {
        if(b==null){
            return null;
        }
        ObjectInputStream oin;
        try {
            oin = new ObjectInputStream(new ByteArrayInputStream(b));
            try {
                return (T) oin.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
}
