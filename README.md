# Ecommerce-PaymentService

Payment Service for Ecommerce Application

## API Documentation

### Payment Controller

#### Initiate Payment
- **Endpoint:** `/api/payment/initiate_payment`
- **Method:** `POST`
- **Description:** Initiates a payment process.
- **Request Body:**
  - `InitiatePaymentRequestDto` (e.g., amount, currency, user details, etc.)
- **Response:**
  - `200 OK`: Returns the payment initiation details.
  - `400 Bad Request`: If the request is invalid.
  - `500 Internal Server Error`: If there is an error during payment initiation.

### Payment Gateway Controller

#### Add Payment Gateway
- **Endpoint:** `/api/payment/gateway/add`
- **Method:** `POST`
- **Description:** Adds a new payment gateway.
- **Request Body:**
  - `PaymentGatewayDto` (e.g., gateway name, configuration details, etc.)
- **Response:**
  - `201 Created`: Returns the added payment gateway details.
  - `400 Bad Request`: If the request is invalid.
  - `500 Internal Server Error`: If there is an error during the addition of the payment gateway.
