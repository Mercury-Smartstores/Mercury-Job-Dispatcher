# Mercury Job Dispatcher 🎏 

The Mercury Job Dispatcher assesses customers using metadata such as their poses and distances to the shelves, as well as information from the shelves to reshuffle a prism structure which completely contains the product exposed. In this sense, the Job Dispatcher merely calculates the minimum distance from the customer to the prisms to estimate
the likelihood of the customer taking or dropping an item. 

Shelves Prisms            |  Pose Estimated |  Distance from wrists to prisms
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/Mercury-Smartstores/Mercury-Job-Dispatcher/blob/mainline/readme-assets/shelves_prisms.png" alt="Shelves Prisms" width="250"/>  |  <img src="https://github.com/Mercury-Smartstores/Mercury-Job-Dispatcher/blob/mainline/readme-assets/skeletals_annotated.png" alt="Pose Estimated for Customers" width="250"/> |  <img src="https://github.com/Mercury-Smartstores/Mercury-Job-Dispatcher/blob/mainline/readme-assets/distance_wrists_shelf.png" alt="Distance from wrists to prisms" width="250"/>

### 🔧 Installation

The Job Dispatcher is an easy-to-setup [Maven](https://maven.apache.org/) project,
so you can automatically download the dependencies of this project. 

### 📦 Deploying

Note that this package contains the main functionalities for a Job Dispatcher in Mercury
to operate, but it does not handle communication with other units. You must provide
the code to be able to connect this component with other Mercury modules.

### 📄 License

This repository is licensed under the [MIT License](LICENSE).

### ♦️ About Mercury

This repository contains the code for a package from the Mercury infrastructure.
If you want to know more about Mercury check out the [Mercury Organization](https://github.com/Mercury-Smartstores).
