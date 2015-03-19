
import math

class randomMove:


	def __init__(self, FEN, mappen):
		self.FEN = FEN
		self.turn = ""
		self.row = ["H", "G", "F", "E", "D", "C", "B", "A"]		
		self.col = ["1", "2", "3", "4", "5", "6", "7", "8"]
		self.board = [[None for i in range(8)] for j in range(8)]
		self.moves = []
		self.dic = mappen

	def parseFEN(self):
		newFEN = self.FEN.replace("\"", "")
		FENlist = newFEN.split(":")
		if(FENlist[0]== "FENB"):
			self.turn = "B"
		if(FENlist[0] == "FENW"):
			self.turn = "W"
		FENlist.pop(0)
		whiteString = FENlist[0]
		blackString = FENlist[1]
		self.board = self.getPieceLocations(whiteString, "W")
		self.board = self.getPieceLocations(blackString, "B")
		return self.board

	def getPieceLocations(self,string, color):
		newStr = ""
		if ("W" in string):
			newStr = string.replace("W","")
		elif("B" in string):
			newStr = string.replace("B", "")
		stringpositions = newStr.split(",")
		pieces = []
		for i in stringpositions:
			pieces.append(int(i))
		for j in pieces:
			[x,y] = self.dic[j]
			self.board[x][y] = color
		return self.board


	def getSingleMoves(self):
		for i in range(len(self.board)):
			for j in range(len(self.board[i])):
				if(self.turn == "B"):
					if(self.insideBoard(i+1, j+1)):
						if (self.board[i][j] == self.turn and self.board[i+1][j+1]==None):
							self.upRight(i,j)
					if(self.insideBoard(i+1, j-1)):
						if(self.board[i][j] == self.turn and self.board[i+1][j-1]==None):
							self.upLeft(i,j)
				elif(self.turn == "W"):
					if (self.insideBoard(i-1, j-1)):		
						if (self.board[i][j] == self.turn and self.board[i-1][j-1]==None):
							self.downLeft(i,j)
					if(self.insideBoard(i-1, j+1)):
						if(self.board[i][j] == self.turn and self.board[i-1][j+1]==None):
							self.downRight(i,j)							
		return self.moves


	def insideBoard(self, i , j):
		if (i<=7 and i>=0 and j<=7 and j>=0):
			return True
		else:
			return False

	def upRight(self, i, j):
		self.moves.append(self.col[i]+self.row[j]+ "-"+ self.col[i+1]+ self.row[j+1])

	def upLeft(self, i, j):
		self.moves.append(self.col[i]+self.row[j]+ "-"+ self.col[i+1]+ self.row[j-1])
				
	def downRight(self, i, j):
		self.moves.append(self.col[i]+self.row[j]+ "-"+ self.col[i-1]+self.row[j+1])
						

	def downLeft(self, i, j):
		self.moves.append(self.col[i]+self.row[j]+ "-"+ self.col[i-1]+ self.row[j-1])


	def getConqueringMoves(self):
		for i in range(len(self.board)):
			for j in range(len(self.board[i])):
				if(self.turn == "W" and self.board[i][j]== self.turn) :
					self.moves.append(self.whiteRecursiveConquering(i,j, self.col[i]+self.row[j]))
				if(self.turn == "B" and self.board[i][j]== self.turn) :
					self.moves.append(self.blackRecursiveConquering(i,j, self.col[i]+self.row[j]))
		x = 0
		while (x<len(self.moves)):
			if not "-" in self.moves[x]:	
				self.moves.pop(x)
			else:
				x = x+1
		return self.moves

	def blackRecursiveConquering(self, x, y, singleMove):
		if(self.insideBoard(x+2,y+2)):
			if (self.board[x+1][y+1] == "W" and self.board[x+2][y+2]==None):
				return self.blackRecursiveConquering(x+2, y+2, singleMove + "-" + self.col[x+2] + self.row[y+2])
		if(self.insideBoard(x+2, y-2)):
			if (self.board[x+1][y-1] == "W" and self.board[x+2][y-2]==None):
				return self.blackRecursiveConquering(x+2, y-2, singleMove + "-" + self.col[x+2]+ self.row[y-2])
		return singleMove


	def whiteRecursiveConquering(self, x, y, singleMove):
		if(self.insideBoard(x-2,y-2)):
			if (self.board[x-1][y-1] == "B" and self.board[x-2][y-2]==None):
				return self.whiteRecursiveConquering(x-2, y-2, singleMove+ "-"+ self.col[x-2] + self.row[y-2])
		if(self.insideBoard(x-2, y+2)):
			if (self.board[x-1][y+1] == "B" and self.board[x-2][y+2]==None):
				return self.whiteRecursiveConquering(x-2, y+2, singleMove+ "-" + self.col[x-2]+ self.row[y+2])
		return singleMove

	def pickRandom(self):
		getConqueringMoves()
		if (len(self.moves) == 0):
			getSingleMoves()
		if len(self == 0):
			return "no valid moves"
		return self.moves[randint(0,len(moves)-1)]

di = dict([(4, [0,7]), (3, [0,5]), (2, [0,3]), (1, [0,1]), (5, [1,0]), (6, [1,2]), (7, [1, 4]), (8, [1,6]), (9, [2,1]), (10, [2,3]), (11, [2,5]), (12, [2,7]), (13, [3, 0]), (14, [3,2]), (15, [3,4]), (16, [3,6]), (17, [4,1]), (18, [4,3]), (19, [4,5]), (20, [4,7]), (21, [5, 0]), (22, [5,2]), (23, [5,4]), (24, [5,6]), (25, [6,1]), (26, [6,3]), (27, [6,5]), (28, [6, 7]), (29, [7,0]), (30, [7,2]), (31, [7,4]), (32, [7,6])])
x = randomMove("FEN\"W:W18,19,30,32:B6,8,15", di)
x.parseFEN()
for i in x.board:
	print i
print x.getConqueringMoves()
print x.board[0][0]