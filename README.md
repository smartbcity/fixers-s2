# S2

Automate 


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