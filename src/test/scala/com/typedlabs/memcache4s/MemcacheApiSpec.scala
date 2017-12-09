package com.typedlabs.memcached4s

import scala.concurrent.Future
import scala.concurrent.duration._
import org.scalatest._

import net.spy.memcached.MemcachedClient
import java.net.InetSocketAddress

import com.typedlabs.memcached4s.{Done => MDone}

case class MemcacheApiTest(id: Int)

class MemcacheApiSpec extends AsyncFlatSpec  {

  val testObject = MemcacheApiTest(1)

  val address = new InetSocketAddress("localhost", 11211)
  
  val client: MemcachedClient = new MemcachedClient(address)

  val memcacheApi = new MemcacheApi(client, "test_namespace.")

  "MemcacheApi" should "save a string" in {  
    memcacheApi.set("test", "test").map{ res =>
      assert(res === MDone)
    } 
  }

  "MemcacheApi" should "get a string" in {    
    memcacheApi.get("test").map{ res => 
      assert(res === Some("test"))
    } 
  }

  "MemcacheApi" should "save a MemcacheApiTest case class" in {        
    memcacheApi.set("test_obj", testObject).map{ res =>
      assert(res === MDone)
    } 
  }  

 "MemcacheApi" should "get a MemcacheApiTest case class" in {        
    memcacheApi.get("test_object").map{ res =>
        assert(res === Some(testObject))
    }
  }

 "MemcacheApi" should "getOrElseUpdate a MemcacheApiTest case class" in {        
    memcacheApi.getOrElseUpdate("test_object", 5 minutes)(Future(testObject))
      .map{ res =>
          assert(res === testObject)
      }
  }    

"MemcacheApi" should "remove with a key" in {        
    memcacheApi.remove("test")
      .map{ res =>
        assert(res === MDone)
      }

    memcacheApi.get("test").map{ res => 
      assert(res === None)
    }
  }      

}


