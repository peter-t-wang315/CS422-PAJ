```mermaid
graph TD
    A[PersonalPlannerApplication] -->|Uses| B[LoginController]
    A -->|Uses| C[TagController]
    A -->|Uses| D[TaskController]

    B -->|Interacts with| E[UserService]
    B -->|Interacts with| F[TaskService]

    C -->|Uses| G[TagService]
    C -->|Uses| E

    D -->|Manages| F
    D -->|Manages| E
    D -->|Manages| G

    E -->|Accesses| H[UserRepository]
    F -->|Accesses| I[TaskRepository]
    G -->|Accesses| J[TagRepository]

    K[Tag] -.-> J
    L[Task] -.-> I
    M[Users] -.-> H
