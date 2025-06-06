## 📦 tracking-number-generator-service

### 🔧 Configuration

**Requirements to run this service:**

* Java 17
* Spring Boot 3.x.x

The application runs on **port 8000**.

---

### 🚀 API Usage

#### 1. Generate Tracking Number *(Without Query Parameters)*

**Endpoint:**

```
GET http://localhost:8000/next-tracking-number
```

**Sample Response:**

```json
{
  "trackingNumber": "D2JYU9JNZL8DV8QB",
  "createdAt": "2025-06-06T07:26:14.4891636Z",
  "originCountryId": null,
  "destinationCountryId": null,
  "weight": null,
  "customerId": null,
  "customerName": null,
  "customerSlug": null
}
```

---

#### 2. Generate Tracking Number *(With Query Parameters)*

**Endpoint:**

```
GET http://localhost:8000/next-tracking-number?originCountryId=IN&destinationCountryId=IN&customerSlug=redbox-logistics&weight=12.9kg&customerId=a2327bdd-19e7-4847-b7a4-43d450bb42cd&customerName=Syam Doddi
```

**Sample Response:**

```json
{
  "trackingNumber": "90SMGRTUL5JPLJAO",
  "createdAt": "2025-06-06T07:28:45.1915735Z",
  "originCountryId": "IN",
  "destinationCountryId": "IN",
  "weight": "12.9kg",
  "customerId": "a2327bdd-19e7-4847-b7a4-43d450bb42cd",
  "customerName": "Syam Doddi",
  "customerSlug": "redbox-logistics"
}
```
