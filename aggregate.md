
 * Automate
```
   C1         C2   _C3_   C4
                   \  /  
-------> 0 -------> 1 -------> 2 
```

* Transition
```
C1 -> 0  
0 + C2 -> 1
1 + C3 -> 1
1 + C4 -> 2 
```

* Transition Object
```
C1 -> 0  
0 + C2 -> 1
1 + C3 -> 1
1 + C4 -> 2 
```


fun stateEntered(state: S2State)  
fun stateExited(state: S2State)  

fun eventNotAccepted(event: Command)  

[comment]: <> (fun transition&#40;transition: S2Transition&#41;)

fun transitionStarted(transition: S2Transition)  
fun transitionEnded(transition: S2Transition)  

fun stateMachineStarted(stateMachine: S2Automate)  
fun stateMachineStopped(stateMachine: S2Automate)  

fun stateMachineError(stateMachine: S2Automate, exception: Exception?)  

[comment]: <> (fun extendedStateChanged&#40;key: Any?, value: Any?&#41;  )