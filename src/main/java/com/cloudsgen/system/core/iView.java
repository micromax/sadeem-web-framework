package com.cloudsgen.system.core;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Deprecated
public class iView {


    private String rootp;

    private FileInputStream fin;


    @Deprecated
    public void setRootPath(String rp)
    {
        this.rootp = rp;
    }

    @Deprecated
    public ByteBuffer load(String vpath) throws IOException {


        fin = new FileInputStream(this.rootp + vpath);
        FileChannel fc = fin.getChannel();
        int siz = (int) fc.size();
        ByteBuffer buffer = ByteBuffer.allocate(siz);
        while(fc.read(buffer) >= 0|| buffer.position() > 0){
            buffer.flip();

            buffer.compact();

            //return buffer;

        }


        return buffer;



    }



}
