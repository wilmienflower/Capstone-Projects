import pygame
import random
pygame.init()

pygame.display.set_caption("First Game!") #gives the game a caption

screen_width = 700
screen_height = 1080

window_size = pygame.display.set_mode((screen_height, screen_width))#create the screen

#creating the player and monsters. 

player = pygame.image.load("player.jpg")
monster_one = pygame.image.load("monster.jpg")
monster_two = pygame.image.load("monster2.jpg")
monster_three = pygame.image.load("monster3.jpg")
prize = pygame.image.load("prize.jpg")

#Get the width and height of the images in order to do boundary detection.

player_height = player.get_height()
player_width = player.get_width()

monster_height = monster_one.get_height()
monster_width = monster_one.get_width()

monster_two_height = monster_two.get_height()
monster_two_width = monster_two.get_width()

monster_three_height = monster_three.get_height()
monster_three_width = monster_three.get_width()

prize_height = prize.get_height()
prize_width = prize.get_width()

#store the positions of the player and monsters
playerXPosition = 0
playerYPosition = 0

#monstes are off screen and at random y positions

monster_oneXPosition =  screen_width
monster_oneYPosition =  random.randint(0, screen_height - monster_height)

monster_twoXPosition =  screen_width
monster_twoYPosition =  random.randint(0, screen_height - monster_two_height)

monster_threeXPosition =  screen_width
monster_threeYPosition =  random.randint(0, screen_height - monster_three_height)

prizeXPosition =  screen_width
prizeYPosition =  random.randint(0, screen_height - prize_height)

#check is up/down/left/right key is pressed.

keyUp= False
keyDown = False
keyLeft = False
keyRight = False
 

while 1:
    
    window_size.fill(0) #clears the screen.
    window_size.blit(player, (playerXPosition, playerYPosition))
    window_size.blit(monster_one, (monster_oneXPosition, monster_oneYPosition))
    window_size.blit(monster_two, (monster_twoXPosition, monster_twoYPosition))
    window_size.blit(monster_three, (monster_threeXPosition, monster_threeYPosition))
    window_size.blit(prize, (prizeXPosition, prizeYPosition))
    
    pygame.display.flip()#updates the screen. 
    
    for event in pygame.event.get():
    
        # This event checks if the user quits the program, then if so it exits the program. 
        
        if event.type == pygame.QUIT:
            pygame.quit()
            exit(0)
        #checks if the user presses a key down.
        
        if event.type == pygame.KEYDOWN:
        
            #tests if the key pressed is the one we want.
            
            if event.key == pygame.K_UP: 
                keyUp = True
            if event.key == pygame.K_DOWN:
                keyDown = True
            if event.key == pygame.K_LEFT:
                keyLeft = True
            if event.key == pygame.K_RIGHT:
                keyRight = True
        
        #checks if a key is not being pressed
        
        if event.type == pygame.KEYUP:
        
            #tests if the key released is the one we want.
            
            if event.key == pygame.K_UP:
                keyUp = False
            if event.key == pygame.K_DOWN:
                keyDown = False
            if event.key == pygame.K_LEFT:
                keyLeft = False
            if event.key == pygame.K_RIGHT:
                keyRight = False
    
    if keyUp == True:
        if playerYPosition > 0 : # This makes sure that the user does not move the player above the window.
            playerYPosition -= 1
    if keyDown == True:
        if playerYPosition < screen_height - player_height:# This makes sure that the user does not move the player below the window.
            playerYPosition += 1
    if keyLeft == True:
        if playerXPosition > 0 :
            playerXPosition -= 1
    if keyRight == True:
        if playerXPosition < screen_width - 150:
            playerXPosition += 1
    
    
    #bounding box for the player:
    
    playerBox = pygame.Rect(player.get_rect())
    
    #this updates the playerBox position to the player's position:
    
    playerBox.top = playerYPosition
    playerBox.left = playerXPosition
    playerBox.right = playerYPosition
    playerBox.bottom = playerXPosition
    
    #bounding boxes for the monsters and prize:
    
    monster_oneBox = pygame.Rect(monster_one.get_rect())
    monster_oneBox.top = monster_oneYPosition
    monster_oneBox.left = monster_oneXPosition
    monster_oneBox.bottom = monster_oneXPosition
    monster_oneBox.right = monster_oneYPosition
    
    monster_twoBox = pygame.Rect(monster_two.get_rect())
    monster_twoBox.top = monster_twoYPosition
    monster_twoBox.left = monster_twoXPosition
    monster_twoBox.bottom = monster_twoXPosition
    monster_twoBox.right = monster_twoYPosition
    
    monster_threeBox = pygame.Rect(monster_three.get_rect())
    monster_threeBox.top = monster_threeYPosition
    monster_threeBox.left = monster_threeXPosition
    monster_threeBox.bottom = monster_threeXPosition
    monster_threeBox.right = monster_threeYPosition
    
    prizeBox = pygame.Rect(prize.get_rect())
    prizeBox.top = prizeYPosition
    prizeBox.left = prizeXPosition
    prizeBox.bottom = prizeXPosition
    prizeBox.right = prizeYPosition
    
    #if player collides with monsters player loses
    if playerBox.colliderect(monster_oneBox): 
        
        print("You lose!") 
        
        pygame.quit()
        exit(0)
    if playerBox.colliderect(monster_twoBox):
        
        print("You lose!") 
        
        pygame.quit()
        exit(0)
    if playerBox.colliderect(monster_threeBox):
        
        print("You lose!") 
        
        pygame.quit()
        exit(0)


    if playerBox.colliderect(prizeBox):  #if player collides with prize player wins

        print("You win!")
        
        pygame.quit()
        
        exit(0)
    
 
    
    #makes enemy approach the player:
    
    monster_oneXPosition -= 0.15
    monster_twoXPosition -= 0.25
    monster_threeXPosition -= 0.25
    prizeXPosition -= 0.15
    
   
  
    
