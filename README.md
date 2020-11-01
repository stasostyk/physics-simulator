# Physics Simulator
A physics simulator, with various modes to play around with. This project serves as a method for me to apply and revise my knowledge from IB Physics HL.

## Kinematics Mode
From my physics unit on kinematics in two dimensions, the mode simulates bouncing balls affected by everyday forces, neglecting air resistance.

![Kinematics Demo](images/kinematics_demo.png?raw=true "Kinematics Demo")
* Click your mouse to spawn a ball
* To give it an initial velocity, when you click your mouse, drag it in a direction. The magnitude of your mouse displacement, alongside the angle will give the ball component vectors.
* Note: There are many bugs, such as the "infinite bounce" glitch which you will definitely notice. Well, that's now a feature!

## Dynamics Mode
I found objects on inclines to be the most interesting application of my unit on dynamics, which is why I made this simulation. It lets you observe the various forces acting to accelerate an object on an inclined surface.

![Dynamics Demo](images/dynamics_demo.png?raw=true "Dynamics Demo")
* Change the sliders on the top to dynamically modify the mass, angle of the incline, and force applied.
* Observe the object either rest, or accelerate up/down. Note: sometimes it will stay still for a second then move. This is intentional, as in reality the object would be moving, but the screen has too little pixels to capture this motion at first.
* To respawn and reset the object, click your mouse and let go where you want it to restart. The red lines will help you navigate.

## Compiling and Running
To see these simulations on your own, follow the following steps.
1. Download a Java SDK and add it to your system PATH
2. In your terminal or CMD, navigate to the directory in which you have downloaded this project.
3. Then run:
```
$ javac *.java
$ java Main
```
