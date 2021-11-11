# Yavin Bus Tickets App

# Application architecture:
![Applicaion architecture](https://user.oc-static.com/upload/2018/03/13/15209311930352_final-architecture.png)


# Application architecture explained:
<p align="center">
  <img src="https://google-developer-training.github.io/android-developer-advanced-course-concepts/images/14-1-c-architecture-components/dg_architecture_comonents.png">
</p>

**ViewModel** are objects preserved across configuration changes. They are useful to allow _Caching_ and _avoid work interruption during configuration changes_: loading operations can run uninterrupted inside of them during configuration changes, while the resulting data can be cached in them and shared with one or more Fragments/Activity currently attached to it.

**LiveData** is a simple observable data holder class that is also lifecycle-aware. New values are only dispatched to observers when their lifecycle is at least in the STARTED (visible) state, and observers are unregistered automatically which is handy to avoid memory leaks. LiveData is useful to allow _Caching_ and _Avoid background work_: it caches the latest value of the data it holds and that value is automatically dispatched to new observers. Plus, it is notified when there are no more registered observers in the STARTED state, which allows to avoid performing unnecessary background work.


# Clean architecture layers:

**Presentation Layer** contains UI (Activities & Fragments) that are coordinated by Presenters/ViewModels which execute 1 or multiple Use cases. 

**Presentation Layer** depends on Domain Layer.

**Domain Layer** is the most INNER part of the onion (no dependencies with other layers) and it contains Entities, Use cases & Repository Interfaces. Use cases combine data from 1 or multiple Repository Interfaces.
Data Layer contains Repository Implementations and 1 or multiple Data Sources. Repositories are responsible to coordinate data from the different Data Sources. Data Layer depends on Domain Layer.


# Repository Pattern Design:

![Repository Pattern Design](https://miro.medium.com/max/2260/1*xxr1Idc8UoNELOzqXcJnag.png)

**The above design allow to avoid the bellow mistakes:**

- The Repository returns a DTO instead of a Domain Model.
- DataSources (ApiServices, Daos..) use the same DTO.
- There is a Repository per set of endpoints and not per Entity (or Aggregate Root if you like DDD).
- The Repository caches the whole model, even those fields that need to be always up to date.
- A DataSource is used by more than one Repository.






