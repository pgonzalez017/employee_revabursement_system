# Employee Revabursement System

NOTE: The backend is already deployed and accessible at this url: 
http://employeerevabursementsystem-env.eba-s9jgpymb.us-east-2.elasticbeanstalk.com

## Project Requirements

1. Create a Front End that will allow employees and managers to create, view and approve reimbursements.
2. Users should be able to create and log in to their account.
3. The backend API is setup with Spring Security and protected by authentication. You must utilize JWTs to access the backend.
4. The currently logged in user should be associated with the action. If they are creating the reimbursement, they should be the "author", 
if they are resolving the reimbursement, they should be the "resolver".

## API Documentation and Snippets

### Users
> GET **/users** : retrieves all users as a pageable. 
  Optional request parameters (page, offset, sort, order)

> GET **/users/{userId}** : retrieves a single user by their id.


> POST **/users** : creates a new user. 
>  Required fields {username (unique), password, email(unique)}. Optional fields {firstName, lastName, authority ("EMPLOYEE", "MANAGER", "ADMIN", "LOCKED")}

> POST **/users/authenticate** : authenticates a user and returns a JWT
  Required fields {username, password}
  NOTE: The JWT returned must be passed as a Bearer token in the headers of your fetch call in order to access the restricted Reimbursements endpoint.

### Reimbursements
> GET **/reimbursements** : retrieves all reimbursements as a pageable. 
  Optional request parameters (page, offset, sort, order)

> GET **/reimbursements/{id}** : retrieves a single reimbursement by its id.

> POST **/reimbursements** : creates a new reimbursement
  Required Fields { amount, description, authorUsername (user must exist), reimbursementStatus(defaults to Pending), reimbursementType("Lodging", "Travel", "Food", "Other")}

> POST **/reimbursements/resolve** : resolves/updates a reimbursement that already exists.
  Required Fields { id, resolverUsername, reimbursementStatus }

