package com.cloudsgen.system;

import com.cloudsgen.system.core.CG_Controller;
import com.cloudsgen.system.core.Config;
import com.cloudsgen.system.core.RouterManager;
import com.cloudsgen.system.utils.LuncherHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.streams.Pump;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Luncher extends AbstractVerticle {


    public static Config getConfig = new Config();
    public static RouterManager Rm = new RouterManager();
    public java.util.Map<String, String> RouterMap = Rm.getRouterMap();
    public static LuncherHelper LHelp =  LuncherHelper.getInstance();
    private int port;
    private String ServerIP;




    public static Vertx vertx ;
    public Luncher() {
        //getConfig.getServerConf().get();
        vertx = getVertx();
    }




    @Override
    public void start() throws Exception {
        vertx = getVertx();

        try {
            System.setProperty("vertx.disableFileCaching", "false");
          //  System.setProperty("vertx.disableFileCaching", getConfig.getServerConf().get("disableFileCaching"));
        }catch (NullPointerException ne){
            ne.printStackTrace();
        }

        HttpServerOptions options = new HttpServerOptions();
        options.setReuseAddress(Boolean.parseBoolean(getConfig.getServerConf().get("ReuseAddress")));
        options.setReusePort(Boolean.parseBoolean(getConfig.getServerConf().get("ReuseAddPort")));
        options.setHandle100ContinueAutomatically(Boolean.parseBoolean(getConfig.getServerConf().get("Handle100ContinueAutomatically")));
        options.setTcpNoDelay(Boolean.parseBoolean(getConfig.getServerConf().get("TcpNoDelay")));
        options.setTcpFastOpen(Boolean.parseBoolean(getConfig.getServerConf().get("TcpFastOpen")));
        options.setTcpQuickAck(Boolean.parseBoolean(getConfig.getServerConf().get("TcpQuickAck")));
        options.setTcpKeepAlive(Boolean.parseBoolean(getConfig.getServerConf().get("TcpKeepAlive")));
        //options.setMaxChunkSize(Integer.parseInt(getConfig.getServerConf().get("MaxChunkSize")));

     //   options.setTcpCork(true);

        HttpServer server = vertx.createHttpServer(options);

        Router router = Router.router(vertx);


        router.route().handler(BodyHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        router.route().handler(CookieHandler.create());


        server.connectionHandler(conn -> {


            conn.exceptionHandler(ex -> {
                System.out.println(conn.remoteAddress() + " is Closed with error");
                ex.printStackTrace();
            });

            conn.closeHandler(clos -> {

                System.out.println(conn.remoteAddress() + " is Closed");

            });


        });





        router.route("/static/*").handler(StaticHandler.create());

        router.route("/static/*").handler(ctx -> {


            if (ctx.statusCode() == -1) {
                System.out.println("status -1");
                if(ctx.response().closed() == false) {
                    ctx.response().setStatusCode(404).end();
                }else
                {
                    ctx.response().setStatusCode(404);
                    ctx.next();
                    ctx.response().close();
                    //ctx.end();
                }


            } else {
                StaticHandler.create();
            }

        });



        router.route("/files/*").handler(ctx -> {


            String URL = ctx.request().path();
            getFile( URL ,  ctx);



        });


        router.route("/*").handler(routingContext -> {



            String URL = routingContext.request().path();
            String[] controller = URL.split("/");
           final int URLSigmantSize = controller.length - 1;



            String classz = getConfig.getServerConf().get("MainController");
            String Method = null;
            CG_Controller builts = null;
            System.out.println(URLSigmantSize+URL);




            switch (URLSigmantSize) {


                case -1:
                    try {


                        try {
                            builts = LHelp.GetController(RouterMap.get(classz), routingContext);
                            if(builts != null){
                                if(builts.isRunError() == true)
                                {
                                    builts.error();
                                }else {
                                    builts.index();
                                }

                              }

                        } finally {
                            builts.finalize();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:


                    try {
                        classz = controller[1].toString();

                        try {
                            builts = LHelp.GetController(RouterMap.get(classz), routingContext);
                            if(builts.isRunError() == true)
                            {
                                builts.error();
                            }else {
                                builts.index();
                            }

                        } finally {
                            builts.finalize();
                        }


                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                    break;
                case 2:
                default :



                    try {
                        classz = controller[1].toString();
                        try {
                            builts = LHelp.GetController(RouterMap.get(classz), routingContext);
                            if(builts != null){
                                builts.invoker(controller[2].toString());
                            }

                        } finally {
                             builts.finalize();
                        }


                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        //e.printStackTrace();
                    }





                    break;

            }

            //System.out.println(URL);


            if (routingContext.response().closed()) {
                //System.out.println(routingContext.request().remoteAddress());
            }


        });



        port = Integer.parseInt(getConfig.getServerConf().get("port"));
        ServerIP = getConfig.getServerConf().get("hostname");

        server.requestHandler(router::accept).listen(port);
    }



    private void getFile(String path , RoutingContext rxtx) {
//        final File[] file = {null};
       // rxtx.response().putHeader("content-type","application/JPEG");
        rxtx.response().setChunked(true);
        String[] controller = path.split("/" ,3);
        System.out.println(path+" :  "+controller[2]);
        vertx.fileSystem().open("uploads/"+controller[2], new OpenOptions(), readEvent -> {
            try{
                if (readEvent.failed()) {
                    System.out.println("readEvent failed");
                    rxtx.response().setStatusCode(500).end();
                    return;
                }

                AsyncFile asyncFile = readEvent.result();

                   Pump.pump(asyncFile, rxtx.response()).start();


                asyncFile.endHandler(a -> {
//                    file[0] = (File) asyncFile;
                    asyncFile.close();
                    rxtx.response().end();
                    rxtx.response().close();
                    System.out.println("file downloaded");

                });



            }catch(IllegalStateException ignored){
                rxtx.response().end();

                rxtx.response().close();
                ignored.printStackTrace();
            }
        });
//        return file[0];
    }



}
