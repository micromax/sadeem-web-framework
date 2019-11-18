package com.cloudsgen.system.core;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;




public class load {
    public final static Configuration cfg = new Configuration();
    //public static load load = new load();
    private RoutingContext Rctx;

    private String rootp;
    private FileInputStream fin;
    public ByteBuffer buffer;
    public static FileTemplateLoader ftl;
    public static TemplateLoader[] tml;
    //public static load getInstance() {
    //    return load;
    // }
    public load() {
        // TODO Auto-generated constructor stub

        cfg.setTemplateUpdateDelay(600000);
    }

    public void setRootPath(String rp)
    {
        this.rootp = rp;

    }



    @SuppressWarnings("For testing only no Templting functnalty in ths method")
    @Deprecated
    public ByteBuffer view(String fp) throws IOException{

        fin = new FileInputStream(this.rootp + fp);
        FileChannel fc = fin.getChannel();
        int siz = (int) fc.size();
        buffer = ByteBuffer.allocate(siz);
        while(fc.read(buffer) >= 0|| buffer.position() > 0){
            buffer.flip();

            buffer.compact();
            System.out.println(buffer.remaining());
            return buffer;

        }

        fin.close();
        fc.close();
        return buffer;


    }



    @SuppressWarnings("For testing only no Templting functnalty in ths method")
    @Deprecated
    public ByteBuffer view0(String fp) throws IOException{

        RandomAccessFile aFile = new RandomAccessFile(this.rootp + fp, "r");
        FileChannel inChannel = aFile.getChannel();
        int siz = (int) inChannel.size();
        ByteBuffer buffer = ByteBuffer.allocate(siz);

        while(inChannel.read(buffer) > 0)
        {
            buffer.flip();


            // do something with the data and clear/compact it.
            buffer.clear();
        }
        //buffer.clear();
        inChannel.close();
        aFile.close();

        return buffer;


    }


    /**
     *
     *
     *
     *
     *
     * @param fp "Ptah to View Temp File end with .ftl"
     * @param map " data to passed to view"
     * @return ByteBuffer
     */

    public ByteBuffer viewAsByteBuffer(String fp  , Map<String , Object> map ) {


        StringWriter buffer = new StringWriter();
        try {
            ftl = new FileTemplateLoader(new File(this.rootp));
            tml = new TemplateLoader[] {ftl};
            MultiTemplateLoader mtl = new MultiTemplateLoader(tml);
            cfg.setTemplateLoader(mtl);



            Template ht = cfg.getTemplate(fp);
            ht.process(map, buffer);
            map.clear();
            tml = null;
            ftl = null;
        } catch (IOException | TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                super.finalize();
                tml = null;
                ftl = null;


            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return ByteBuffer.wrap(buffer.toString().getBytes()).compact();
    }


    /**
     *
     *
     *
     *
     * @param fp "Ptah to View Temp File end with .ftl"
     * @param rxtx RoutingContext
     * @return String
     */

    public String View(String fp  , RoutingContext rxtx  )
    {

        String path = "/";


        StringWriter buffer = new StringWriter();
        try {
            Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_23);
            FileTemplateLoader ftl2 = new FileTemplateLoader(new File(path));
            ClassTemplateLoader ctl = new ClassTemplateLoader(new load().getClass(), path);
            MultiTemplateLoader mtl = new MultiTemplateLoader(new TemplateLoader[] {  ftl2, ctl });

            freemarkerConfiguration.setTemplateLoader(mtl);
            //freemarkerConfiguration.setClassForTemplateLoading(UltraVxMain.class, "/templates/");

            Template freemarkerTemplate = freemarkerConfiguration.getTemplate(fp);


            freemarkerTemplate.process(rxtx, buffer);

        } catch  (Exception  e) {
            // TODO Auto-generated catch block
            rxtx.response().end(  e.getMessage() );

        }
        finally {
            try {
                super.finalize();

            } catch (Throwable e) {
                // TODO Auto-generated catch block
                rxtx.response().end(  e.getMessage() );
            }

        }
        return buffer.toString();
    }



    protected void finalize() throws Throwable {

        try {
            this.buffer.clear();
            this.fin.close();
            //Runtime.getRuntime().gc();
        }
        finally {
            super.finalize();
            this.buffer.clear();
            this.fin.close();
        }

    }


    /**
     *
     * @param fp Path for Template File
     * @param map user data
     * @param rxtx RoutingContext
     *
     */


    @SuppressWarnings("not tesed and has many bugs with our life cycle")
    public void View(String fp  , Map<String , Object> map , RoutingContext rxtx)
    {
        FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();


        rxtx.put("USERDATA" , map);
        engine.render(rxtx, fp, res -> {
            if (res.succeeded()) {
                rxtx.response().end(res.result());
            } else {
                rxtx.fail(res.cause());
            }
        });
    }




    /**
     *
     * @param fp Path for Template File
     *
     * @param rxtx RoutingContext
     *
     */


    @SuppressWarnings("not tesed and has many bugs with our life cycle")
    public void RView( RoutingContext rxtx , String fp )
    {
        FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();



        engine.render(rxtx, fp, res -> {
            if (res.succeeded()) {
                rxtx.response().end(res.result());
            } else {
                rxtx.fail(res.cause());
            }
        });
    }





}

