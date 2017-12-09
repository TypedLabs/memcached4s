# memcached4s

memcached4s is memcache wrapper over spymemcache. It's foundations are based on the excellent code from @mumoshu and its [play2-memcached](https://github.com/mumoshu/play2-memcached) module.

I wanted a simple scala memcached client for the services layer on an application I was working on, I tried a few option in the scala ecosystem but did not find something that worked simply like @mumoshu solution.

memcached4s is


1. [SBT](#sbt)
2. [Usage](#usage)

## Quick start

### SBT

[ ![Download](https://api.bintray.com/packages/typedlabs/releases/memcached4s/images/download.svg) ](https://bintray.com/typedlabs/releases/memcached4s/_latestVersion)

In `plugins.sbt`, add the following bintray resolver

```scala
Resolver.bintrayIvyRepo("typedlabs", "releases")
```

In `build.sbt`, set the memcached4s version in a variable (for the latest version, set `val memcached4sVersion =` the version you see
in the bintray badge above).

```scala
libraryDependencies ++= Seq(
    "com.typedlabs" %% "memcached4s" % memcached4sVersion
)
```

### Usage

memcached4s leverages Scala **Promise** to interface with spymemcache asynchronous api via a single case class **com.typedlabs.memcached4s.MemcacheApi**. It also has **com.typedlabs.memcached4s.Done** to more clearly signal completion intent rather than `Unit`.

It needs an implicit **scala.concurrent.ExecutionContext**.

```scala

  import com.typedlabs.memcached4s.{MemcacheApi,  Done}
  import net.spy.memcached.{ MemcachedClient }  
  import java.net.InetSocketAddress
  import scala.concurrent.Future
  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global

  val address = new InetSocketAddress("localhost", 11211)
  
  val client: MemcachedClient = new MemcachedClient(address)

  val memcacheApi = new MemcacheApi(client, "test_namespace.")

  val savedData: Future[Done] = memcacheApi.set("some_key", "some_value", 5 minute)

  val retrievedData: Future[Option[String]] = memcacheApi.get("some_key")

  val getOrUpdateData: Future[Option[String]] = memcacheApi.getOrElseUpdate("some_key", 5 minutes)(Future("some_value"))  
  
```

### TODO

Somethings I would like to add at some point.

- [ ] Custom Serialization