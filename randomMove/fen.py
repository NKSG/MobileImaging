import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web
import randomMove

from tornado.options import define, options
define("port", default=8000, help="run on the given port", type=int)

class IndexHandler(tornado.web.RequestHandler):
    def get(self):

        fen = self.get_argument('fen')

        fen = fen.replace("]", "")
        fen = fen.replace("[", "")

        self.write(randomMove.main(fen))

if __name__ == "__main__":
    tornado.options.parse_command_line()
    app = tornado.web.Application(handlers=[(r"/", IndexHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()
