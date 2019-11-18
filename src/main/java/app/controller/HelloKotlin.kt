package app.controller

import com.cloudsgen.system.core.CG_Controller
import io.vertx.ext.web.RoutingContext
import java.io.IOException
import java.util.*

class HelloKotlin (rxtx: RoutingContext) : CG_Controller(rxtx){




    /**
     * index : this default method for every controller
     * Render :  will Print any thing to response
     * simply this will print $String "Hello world ! from Kotlin"
     *
     * @return void
     * @throws IOException
     */
    @Throws(IOException::class)
    override fun index() {
        super.index()
        Render("Hello world ! from Kotlin")
    }


    fun templetwithdata() { //push List to view :-) you can push any type of data this just demonstration
        val data: MutableList<String> = ArrayList()
        data.add("free")
        data.add("open source")
        data.add("Framework from sadeem-egypt.com")
        data.add("Support Kotlin , java , Scala in same project ")
        data.add("Actor and Vertical from AKKA and vertx.io are supported")
        data.add("35,0000 thousand message per second , high performance concurrent built-in TCP server")
        //push simple String var to view
        this.getRxtx().put("title", "This clouds-gen.com Framework 'Kotlin&java <3' from <a href='http://sadeem-egypt.com'>Sadeem-egypt.com </a>")
        this.getRxtx().put("features", data)
        //hellomaster.ftl is master view include tiny view's and partial views
//this.load.View( viwe path , we pass always this.getRxtx() to view this will make all data available
        Render(this.load.View("views/hellomaster.ftl", this.getRxtx()))
    }


}
