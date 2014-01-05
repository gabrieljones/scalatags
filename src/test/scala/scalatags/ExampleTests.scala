package scalatags

import org.scalatest._

import Util._

/**
 * A set of examples used in the documentation.
 */
class ExampleTests extends FreeSpec{
  "Hello World" in strCheck(
    html(
      head(
        script("some script")
      ),
      body(
        h1("This is my title"),
        div(
          p("This is my first paragraph"),
          p("This is my second paragraph")
        )
      )
    ),
    """<html>
         <head>
           <script>some script</script>
         </head>
         <body>
           <h1>This is my title</h1>
           <div>
             <p>This is my first paragraph</p>
             <p>This is my second paragraph</p>
           </div>
         </body>
       </html>"""
  )
  "Variables" in strCheck(
    {
      val title = "title"
      val numVisitors = 1023

      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my ", title),
          div(
            p("This is my first paragraph"),
            p("you are the ", numVisitors.toString, "th visitor!")
          )
        )
      )
    },
    """<html>
         <head>
            <script>some script</script>
         </head>
         <body>
           <h1>This is my title</h1>
           <div>
             <p>This is my first paragraph</p>
             <p>you are the 1023th visitor!</p>
           </div>
         </body>
       </html>"""
  )
  "Control Flow" in strCheck(
    {
      val numVisitors = 1023
      val posts = Seq(
        ("alice", "i like pie"),
        ("bob", "pie is evil i hate you"),
        ("charlie", "i like pie and pie is evil, i hat myself")
      )

      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my title"),
          div("posts"),
          for ((name, text) <- posts) yield (
            div(
              h2("Post by ", name),
              p(text)
            )
          ),
          if(numVisitors > 100)(
            p("No more posts!")
          )else(
            p("Please post below...")
          )
        )
      )
    },
    """<html>
         <head>
           <script>some script</script>
         </head>
         <body>
           <h1>This is my title</h1>
           <div>posts</div>
           <div>
             <h2>Post by alice</h2>
             <p>i like pie</p>
           </div>
           <div>
             <h2>Post by bob</h2>
             <p>pie is evil i hate you</p>
           </div>
           <div>
             <h2>Post by charlie</h2>
             <p>i like pie and pie is evil, i hat myself</p>
           </div>
           <p>No more posts!</p>
         </body>
       </html>"""
  )
  "Functions" in strCheck(
    {
      def imgBox(source: String, text: String) =
        div(
          img(src~source),
          div(
            p(text)
          )
        )

      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my title"),
          imgBox("www.mysite.com/imageOne.png", "This is the first image displayed on the site"),
          div(`class`~"content")(
            p("blah blah blah i am text"),
            imgBox("www.mysite.com/imageTwo.png", "This image is very interesting")
          )
        )
      )
    },
    """<html>
         <head>
           <script>some script</script>
         </head>
         <body>
           <h1>This is my title</h1>
           <div>
             <img src="www.mysite.com/imageOne.png"/>
             <div>
               <p>This is the first image displayed on the site</p>
             </div>
           </div>
           <div class="content">
             <p>blah blah blah i am text</p>
             <div>
               <img src="www.mysite.com/imageTwo.png"/>
               <div>
                 <p>This image is very interesting</p>
               </div>
             </div>
           </div>
         </body>
       </html>"""
  )
  "Custom Attributes" in strCheck(
    html(
      head(
        script("some script")
      ),
      body(
        h1("This is my title"),
        div(
          p(onclick~"... do some js")(
            "This is my first paragraph"
          ),
          a(href~"www.google.com")(
            p("Goooogle")
          )
        )
      )
    ),
    """<html>
         <head>
           <script>some script</script>
         </head>
         <body>
           <h1>This is my title</h1>
           <div>
             <p onclick="... do some js">This is my first paragraph</p>
             <a href="www.google.com">
               <p>Goooogle</p>
             </a>
           </div>
         </body>
       </html>"""
  )
  "Attributes" in strCheck(
    html(
      head(
        script("some script")
      ),
      body(
        h1("This is my title"),
        div(
          p(onclick~"... do some js")(
            "This is my first paragraph"
          ),
          a(href~"www.google.com")(
            p("Goooogle")
          )
        )
      )
    ),
    """<html>
         <head>
           <script>some script</script>
         </head>
         <body>
           <h1>This is my title</h1>
           <div>
             <p onclick="... do some js">This is my first paragraph</p>
             <a href="www.google.com">
               <p>Goooogle</p>
             </a>
           </div>
         </body>
       </html>"""
  )
  "Classes and CSS" in strCheck(
    html(
      head(
        script("some script")
      ),
      body(
        h1(color~"red", backgroundColor~"blue")("This is my title"),
        div(color~"red", backgroundColor~"blue")(
          p.cls("contentpara", "first")(
            "This is my first paragraph"
          ),
          a(opacity~0.9)(
            p.cls("contentpara")("Goooogle")
          )
        )
      )
    ),
    """<html>
         <head>
           <script>some script</script>
         </head>
         <body>
           <h1 style="color:red;background-color:blue;">This is my title</h1>
           <div style="color:red;background-color:blue;">
             <p class="contentpara first">This is my first paragraph</p>
             <a style="opacity:0.9;">
               <p class="contentpara">Goooogle</p>
             </a>
           </div>
         </body>
       </html>"""
  )


  "Layouts" in strCheck(
  {
    def page(scripts: Seq[Node], content: Seq[Node]) =
      html(
        head(scripts),
        body(
          h1("This is my title"),
          div.cls("content")(content)
        )
      )


    page(
      Seq(
        script("some script")
      ),
      Seq(
        p("This is the first ", b("image"), " displayed on the ", a("site")),
        img(src~"www.myImage.com/image.jpg"),
        p("blah blah blah i am text")
      )
    )

  },
  """<html>
       <head>
         <script>some script</script>
       </head>
       <body>
         <h1>This is my title</h1>
         <div class="content">
           <p>This is the first <b>image</b> displayed on the <a>site</a></p>
           <img src="www.myImage.com/image.jpg"/>
           <p>blah blah blah i am text</p>
         </div>
       </body>
     </html>"""
  )



  "Inheritence" in strCheck(
    {
      class Parent{
        def render = html(
          headFrag,
          bodyFrag

        )
        def headFrag = head(
          script("some script")
        )
        def bodyFrag = body(
          h1("This is my title"),
          div(
            p("This is my first paragraph"),
            p("This is my second paragraph")
          )
        )
      }

      object Child extends Parent{
        override def headFrag = head(
          script("some other script")
        )
      }

      Child.render
    },
    """<html>
         <head>
           <script>some other script</script>
         </head>
         <body>
           <h1>This is my title</h1>
           <div>
             <p>This is my first paragraph</p>
             <p>This is my second paragraph</p>
           </div>
         </body>
       </html>"""
  )



  "Unparsed" in strCheck(
    {
      val input = "<p>i am a cow</p>"

      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my title"),
          input
        )
      )
    },
    """<html>
         <head>
           <script>some script</script>
         </head>
         <body>
           <h1>This is my title</h1>
           <p>i am a cow</p>
         </body>
       </html>"""
  )
}
