import tkinter as tk, random


class SnakeGUI:
    # ==========================================
    # Purpose:
    # each object of this class represents a game of snake
    #
    # Instance variables:
    # win - tkinter window
    # canvas - tkinter canvas, used for snake game ui
    # board - rectangle used to define the bounds of the game
    # food_xcor - the food pellet's x coordinate
    # food_ycor - the food pellet's y coordinate
    # first_food - the first food pellet, represented as a circle on the board
    # food_location - a list tracking the current food pellet
    # player - the snake object controlled by the player
    # enemy - the snake object controlled by the computer
    # game_over - a Boolean tracking whether a snake has died
    # blue - a blue portal object
    # orange - an orange portal object
    #
    # Methods: (What methods does this class have, and what does each do?)
    # __init__ - initializes the game
    # gameloop - causes events in the game to take place over time, movement, eating, teleporting etc
    # eat - causes a snake to eat a food pellet if it's head is on the pellet
    # restart - restarts the game back to it's original state
    # shoot_blue - shoots a blue portal
    # shoot_orange shoots an orange portal
    # ai_pathfind - determines how the computer will control the enemy snake
    # ==========================================
    def __init__(self):
        self.win = tk.Tk()
        self.win.title("Counter-Snake: Garter Offensive")
        self.canvas = tk.Canvas(self.win, width = 660, height = 660)
        self.canvas.pack()
        self.board = self.canvas.create_rectangle(30, 30, 630, 630)
        self.food_xcor = random.randrange(30,630, 30)
        self.food_ycor = random.randrange(30,630, 30)
        self.first_food = self.canvas.create_oval(self.food_xcor, self.food_ycor, self.food_xcor + 30,
                                            self.food_ycor + 30,  fill = 'pink')
        self.food_location = [self.first_food]
        self.player = Snake(60, 570, 'grey', self.canvas)
        self.enemy = Snake(570, 60, 'white', self.canvas)
        self.enemy.vx = -30
        self.game_over = False
        self.win.bind('<Down>', self.player.go_down)
        self.win.bind('<Up>', self.player.go_up)
        self.win.bind('<Left>', self.player.go_left)
        self.win.bind('<Right>', self.player.go_right)
        self.win.bind('r', self.restart)
        self.win.bind('p', self.shoot_blue)
        self.win.bind('o', self.shoot_orange)
        self.blue = Portal(1000, 0, '#00a2ff', self.canvas)
        self.orange = Portal(1000, 0, '#ff9a00', self.canvas)
        self.gameloop()

    def gameloop(self):
        if not self.game_over:
            if self.player.out_of_bounds or self.player.on_segment or self.enemy.out_of_bounds or self.player.on_other_check(self.enemy) or self.enemy.on_other_check(self.player):
                self.game_over = True
                self.canvas.create_text(330, 320, text=f'GAME OVER\nSCORE: {len(self.player.segments)}\npress R to restart', justify = 'center')
            if not self.game_over:
                self.player.move(self.food_xcor, self.food_ycor, self.blue, self.orange)
                self.ai_pathfind(self.enemy)
                self.enemy.move(self.food_xcor, self.food_ycor, self.blue, self.orange)
            if self.player.on_food:
                self.eat(self.player)
            if self.enemy.on_food:
                self.eat(self.enemy)
            self.canvas.after(150, self.gameloop)

    def eat(self, snake):
        self.food_xcor = random.randrange(30, 630, 30)
        self.food_ycor = random.randrange(30, 630, 30)
        self.food_location += [self.canvas.create_oval(self.food_xcor, self.food_ycor, self.food_xcor + 30,
                                                self.food_ycor + 30, fill='pink')]
        self.canvas.delete(self.food_location[0])
        del self.food_location[0]

    def restart(self, event):
        self.canvas.delete(tk.ALL)
        self.board = self.canvas.create_rectangle(30, 30, 630, 630)
        self.food_xcor = random.randrange(30, 630, 30)
        self.food_ycor = random.randrange(30, 630, 30)
        self.first_food = self.canvas.create_oval(self.food_xcor, self.food_ycor, self.food_xcor + 30,
                                                  self.food_ycor + 30, fill='pink')
        self.food_location = [self.first_food]
        self.player = Snake(60, 570, 'grey', self.canvas)
        self.enemy = Snake(570, 60, 'white', self.canvas)
        self.enemy.vx = -30
        self.win.bind('<Down>', self.player.go_down)
        self.win.bind('<Up>', self.player.go_up)
        self.win.bind('<Left>', self.player.go_left)
        self.win.bind('<Right>', self.player.go_right)
        self.win.bind('r', self.restart)
        self.win.bind('s', self.shoot_blue)
        self.win.bind('d', self.shoot_orange)
        self.blue = Portal(1000, 0, '#00a2ff', self.canvas)
        self.orange = Portal(1000, 0, '#ff9a00', self.canvas)
        if self.game_over:
            self.game_over = False
            self.gameloop()

    def shoot_blue(self, event):
        if self.player.vx == 30:
            if self.canvas.coords(self.orange.canvas_placement)[0:2] != [630.0, float(self.player.y)]:
                self.canvas.delete(self.blue.canvas_placement)
                self.blue = Portal(630, self.player.y, '#00a2ff', self.canvas)
        elif self.player.vx == -30:
            if self.canvas.coords(self.orange.canvas_placement)[0:2] != [0.0, float(self.player.y)]:
                self.canvas.delete(self.blue.canvas_placement)
                self.blue = Portal(0, self.player.y, '#00a2ff', self.canvas)
        elif self.player.vy == -30:
            if self.canvas.coords(self.orange.canvas_placement)[0:2] != [float(self.player.x), 0.0]:
                self.canvas.delete(self.blue.canvas_placement)
                self.blue = Portal(self.player.x, 0, '#00a2ff', self.canvas)
        elif self.player.vy == 30:
            if self.canvas.coords(self.orange.canvas_placement)[0:2] != [float(self.player.x), 630.0]:
                self.canvas.delete(self.blue.canvas_placement)
                self.blue = Portal(self.player.x, 630, '#00a2ff', self.canvas)

    def shoot_orange(self, event):
        if self.player.vx == 30:
            if self.canvas.coords(self.blue.canvas_placement)[0:2] != [630.0, float(self.player.y)]:
                self.canvas.delete(self.orange.canvas_placement)
                self.orange = Portal(630, self.player.y, '#ff9a00', self.canvas)
        elif self.player.vx == -30:
            if self.canvas.coords(self.blue.canvas_placement)[0:2] != [0.0, float(self.player.y)]:
                self.canvas.delete(self.orange.canvas_placement)
                self.orange = Portal(0, self.player.y, '#ff9a00', self.canvas)
        elif self.player.vy == -30:
            if self.canvas.coords(self.blue.canvas_placement)[0:2] != [float(self.player.x), 0.0]:
                self.canvas.delete(self.orange.canvas_placement)
                self.orange = Portal(self.player.x, 0, '#ff9a00', self.canvas)
        elif self.player.vy == 30:
            if self.canvas.coords(self.blue.canvas_placement)[0:2] != [float(self.player.x), 630.0]:
                self.canvas.delete(self.orange.canvas_placement)
                self.orange = Portal(self.player.x, 630, '#ff9a00', self.canvas)

    def ai_pathfind(self, snake):
        if snake.x == 30 and snake.vx == -30:
            snake.vx = 30
            snake.vy = 0
        if snake.x == 600 and snake.vx == 30:
            snake.vx = -30
            snake.vy = 0
        if snake.y == 30 and snake.vy == -30:
            snake.vx = 0
            snake.vy = 30
        if snake.y == 600 and snake.vy == 30:
            snake.vx = 0
            snake.vy = -30
        if snake.x < self.food_xcor:
            snake.vx = 30
            snake.vy = 0
        elif snake.x > self.food_xcor:
            snake.vx = -30
            snake.vy = 0
        elif snake.y < self.food_ycor:
            snake.vx = 0
            snake.vy = 30
        elif snake.y > self.food_ycor:
            snake.vx = 0
            snake.vy = -30


class Snake:
    # ==========================================
    # Purpose:
    # each object of this class represents a snake in the game
    #
    # Instance variables:
    # x - the x coordinate of the head
    # y - the y coordinate of the head
    # color - the snake's color
    # canvas - the canvas on which the snake is drawn
    # segments - a list containing the tkinter snake body shapes
    # vx - the snake's velocity on the x axis
    # vy - the snake's velocity on the y axis
    # on_food - a Boolean tracking whether the snake is on the food
    # on_segment - a Boolean tracking whether the snake is on a segment of it's body
    # out_of_bounds - a Boolean tracking whether the snake is out of bounds
    # on_other - a Boolean tracking whether the snake is on another snake's body's segment
    # on_blue - a Boolean tracking whether the snake is on the blue portal
    # on_orange - a Boolean tracking whether the snake is on the orange portal
    # last_move - a string representing the last direction moved
    #
    # Methods:
    # __init__ - initializes a snake object
    # move - moves the snake, grows the snake if on food, and teleports the snake if on a portal
    # on_other_check - checks whether the snake's head is on another snake's body
    # go_up - changes the vx and vy to change the snake's direction to up
    # go_down - changes the vx and vy to change the snake's direction to down
    # go_left - changes the vx and vy to change the snake's direction to left
    # go_right - changes the vx and vy to change the snake's direction to right
    # ==========================================
    def __init__(self, x, y, color, canvas):
        self.x = x
        self.y = y
        self.color = color
        self.canvas = canvas
        head = self.canvas.create_rectangle(self.x, self.y, self.x + 30, self.y + 30, fill = self.color)
        self.segments = [head]
        self.vx = 30
        self.vy = 0
        self.on_food = False
        self.on_segment = False
        self.out_of_bounds = False
        self.on_other = False
        self.on_blue = False
        self.on_orange = False
        self.last_move = 'right'

    def move(self, pel_x, pel_y, blue, orange):
        self.on_food = False
        self.on_blue = False
        self.on_orange = False
        if self.x == pel_x and self.y == pel_y:
            self.on_food = True
        if self.x == blue.x and self.y ==blue.y:
            self.on_blue = True
        if self.x == orange.x and self.y ==orange.y:
            self.on_orange = True
        for segment in self.segments:
            if self.x + self.vx == self.canvas.coords(segment)[0] and self.y + self.vy == self.canvas.coords(segment)[1]:
                self.on_segment = True
        if self.x < 30 or self.x > 600 or self.y < 30 or self.y > 600:
            if not self.on_orange and not self.on_blue:
                self.out_of_bounds = True
        if not self.on_blue and not self.on_orange and not self.out_of_bounds:
            self.x += self.vx
            self.y += self.vy
            next_loc = self.canvas.create_rectangle(self.x, self.y, self.x + 30, self.y + 30, fill=self.color)
            if self.vx == 30 and self.vy == 0:
                self.last_move = 'right'
            if self.vx == -30 and self.vy == 0:
                self.last_move = 'left'
            if self.vx == 0 and self.vy == 30:
                self.last_move = 'down'
            if self.vx == 0 and self.vy == -30:
                self.last_move = 'up'
        elif self.on_blue:
            if orange.face == 'right':
                self.x = orange.x + 30
                self.y = orange.y
                self.vx = 30
                self.vy = 0
            if orange.face == 'left':
                self.x = orange.x - 30
                self.y = orange.y
                self.vx = -30
                self.vy = 0
            if orange.face == 'down':
                self.x = orange.x
                self.y = orange.y + 30
                self.vx = 0
                self.vy = 30
            if orange.face == 'up':
                self.x = orange.x
                self.y = orange.y - 30
                self.vx = 0
                self.vy = -30
            next_loc = self.canvas.create_rectangle(self.x, self.y, self.x + 30, self.y + 30, fill=self.color)
        elif self.on_orange:
            if blue.face == 'right':
                self.x = blue.x + 30
                self.y = blue.y
                self.vx = 30
                self.vy = 0
            if blue.face == 'left':
                self.x = blue.x - 30
                self.y = blue.y
                self.vx = -30
                self.vy = 0
            if blue.face == 'down':
                self.x = blue.x
                self.y = blue.y + 30
                self.vx = 0
                self.vy = 30
            if blue.face == 'up':
                self.x = blue.x
                self.y = blue.y -30
                self.vx = 0
                self.vy = -30
            next_loc = self.canvas.create_rectangle(self.x, self.y, self.x + 30, self.y + 30, fill=self.color)
        else:
            next_loc = 'game over location'
        self.segments = [next_loc] + self.segments
        if not self.on_food:
            last_loc = self.segments.pop(-1)
            self.canvas.delete(last_loc)

    def on_other_check(self, other):
        out = False
        for segment in other.segments:
            if self.x == self.canvas.coords(segment)[0] and self.y == self.canvas.coords(segment)[1]:
                out = True
        return out

    def go_down(self, event):
        if self.last_move != 'up' or len(self.segments) == 1:
            self.vx = 0
            self.vy = 30

    def go_up(self, event):
        if self.last_move != 'down' or len(self.segments) == 1:
            self.vx = 0
            self.vy = -30

    def go_left(self, event):
        if self.last_move != 'right' or len(self.segments) == 1:
            self.vx = -30
            self.vy = 0

    def go_right(self, event):
        if self.last_move != 'left' or len(self.segments) == 1:
            self.vx = 30
            self.vy = 0


class Portal:
    # ==========================================
    # Purpose:
    # each object of this class represents a portal
    #
    # Instance variables:
    # canvas - the canvas on which the portal is drawn
    # x - the portal's x coordinate
    # y - the portal's y coordinate
    # color - the portal's color
    # canvas_placement - the portals rank in the tkinter shape list, represented by an integer
    # face - a string representing which direction the portal is facing
    #
    # Methods:
    # __init__ - initializes a portal object
    # ==========================================
    def __init__(self, x, y, color, canvas):
        self.canvas = canvas
        self.x = x
        self.y = y
        self.color = color
        self.canvas_placement = self.canvas.create_rectangle(self.x, self.y, self.x + 30, self.y + 30, fill=self.color)
        if self.x == 0:
            self.face = 'right'
        elif self.x == 630:
            self.face = 'left'
        elif self.y == 0:
            self.face = 'down'
        else:
            self.face = 'up'


SnakeGUI()
tk.mainloop()