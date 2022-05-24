# S2

## Finite State Machine
In the theory of computation, a Mealy machine is a finite-state machine whose output values are determined both by its current state and the current inputs. This is in contrast to a Moore machine, whose (Moore) output values are determined solely by its current state. A Mealy machine is a deterministic finite-state transducer: for each state and input, at most one transition is possible.
[Source Wikipedia](https://en.wikipedia.org/wiki/Mealy_machine)

## Concept

### Automate
```
@JsExport
@JsName("didS2")
fun didS2(): S2Automate{
	return s2<DidId, DidState> {
		name = "DidS2"
		init<DidInitCommand> {
			to = DidState.Created()
			role = DidRole.Admin()
			cmd = DidCreateCommand::class
		}
		transaction<DidCommand> {
			form = DidState.Created()
			to = DidState.Actived()
			role = DidRole.Owner()
			cmd = DidAddPublicKeyCommand::class
		}
		transaction<DidCommand> {
			form = DidState.Actived()
			to = DidState.Actived()
			role = DidRole.Owner()
			cmd = DidAddPublicKeyCommand::class
		}
		transaction<DidCommand> {
			form = DidState.Actived()
			to = DidState.Actived()
			role = DidRole.Owner()
			cmd = DidRevokeCommand::class
		}
		transaction<DidCommand> {
			form = DidState.Actived()
			to = DidState.Revoked()
			role = DidRole.Owner()
			cmd = DidRevokePublicKeyCommand::class
		}
	}
}
```

### State

```kotlin
@Serializable
@JsExport
@JsName("DidState")
open class DidState(
	override val position: Int
	) : S2State {
	@Serializable
	open class Created : DidState(0)
	@Serializable
	open class Actived : DidState(1)
	@Serializable
	open class Revoked : DidState(2)

	override fun toString(): String {
		return this::class.simpleName!!
	}
}
```

###  Command
```
@JsExport
@JsName("DidInitCommand")
interface DidInitCommand : S2InitCommand

@JsExport
@JsName("DidCommand")
interface DidCommand: S2Command<DidId>
```

### Event
```kotlin
@JsExport
@JsName("DidEvent")
interface DidEvent : S2Event<DidState, DidId>
```

### Context

### Guard

current state + command = new state

### Transition

## Automate for Domain Driven Design

### Aggregate

CommandEntryEvent
CommantErrorEvent
CommantGuardEvent
CommantExitEvent

StateContextEntyEvent
StateContextErrorEvent
StateContextExitEvent

CommandSnapEvent
GardSnapEvent
ContextEntrySnapEvent
ContextExitSnapEvent

## State Storing

## Event sourcing

### Decider

F2<COMMAND, EVENT>

```
Build 
    S2AutomateDeciderSpring<Entity, State, Event, Id>

Adapter 
    S2SourcingSpringDataAdapter<Entity, State, Event, Id, S2AutomateDeciderSpring>
    Bean
        EventPersisterData(
            SpringDataEventRepository
        )
        SpringDataEventRepository(
            ReactiveRepositoryFactorySupport
        )
    : S2AutomateDeciderSpringAdapter
        snapLoader: Loader<EVENT, ENTITY, ID>(
            eventStore: EventRepository<EVENT, ID>,
		    snapRepository: SnapRepository<ENTITY, ID>?,
		    evolver: View<EVENT, ENTITY>
        )   
        viewLoader:  Loader<EVENT, ENTITY, ID> (
            eventStore: EventRepository<EVENT, ID>,
		    evolver: View<EVENT, ENTITY>
        )
        AutomateSourcingExecutor<STATE, EVENT, ENTITY, ID>(
            eventPublisher: SpringEventPublisher,
		    eventStore: EventRepository<EVENT, ID>,
		    projectionBuilder: Loader<EVENT, ENTITY, ID>
        )
        class InitBean: InitializingBean(
            AutomateSourcingExecutor<STATE, EVENT, ENTITY, ID>
        ) {
        
        }
```    

==> Remove InitBean

```
Build 
    S2AutomateDeciderSpring<Entity, State, Event, Id>

Adapter 
    S2SourcingSpringDataAdapter<Entity, State, Event, Id, S2AutomateDeciderSpring>(
        AutomateSourcingExecutor<STATE, EVENT, ENTITY, ID>
    )
    Bean
        EventPersisterData(
            SpringDataEventRepository
        )
        SpringDataEventRepository(
            ReactiveRepositoryFactorySupport
        )
    : S2AutomateDeciderSpringAdapter(
        AutomateSourcingExecutor<STATE, EVENT, ENTITY, ID>
    )
        snapLoader: Loader<EVENT, ENTITY, ID>(
            eventStore: EventRepository<EVENT, ID>,
		    snapRepository: SnapRepository<ENTITY, ID>?,
		    evolver: View<EVENT, ENTITY>
        )   
        viewLoader:  Loader<EVENT, ENTITY, ID> (
            eventStore: EventRepository<EVENT, ID>,
		    evolver: View<EVENT, ENTITY>
        )
        AutomateSourcingExecutor<STATE, EVENT, ENTITY, ID>(
            eventPublisher: SpringEventPublisher,
		    eventStore: EventRepository<EVENT, ID>,
		    projectionBuilder: Loader<EVENT, ENTITY, ID>
        )
```    

## More resources:
[spring-statemachine](https://docs.spring.io/spring-statemachine/docs/2.4.0/reference/#introduction)

[Xstate](https://github.com/davidkpiano/xstate)

[blockchain-ssm](https://github.com/smartbcity/blockchain-ssm)

[IBM-DDD](https://developer.ibm.com/languages/java/tutorials/reactive-in-practice-1)
