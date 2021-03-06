import tensorflow as tf
import keras

#load mnist dataset
mnist = keras.datasets.mnist

# Set the traning and test data and labels
(x_train, y_train), (x_test, y_test) = mnist.load_data()

# Normalise the values first so that data is between 0 and 1.
x_train = keras.utils.normalize(x_train, axis=1)
x_test = keras.utils.normalize(x_test, axis=1)

# Define the model

# Sequential is the classic feedfoward architecture
model = keras.models.Sequential()

# Define the input layer it will take the incoming data and flatten it
model.add(keras.layers.Flatten())

# now define the hidden layers. Activation functrions are in tf.nn. relu today is considered default activation
model.add(keras.layers.Dense(units=128, activation=tf.nn.relu))
model.add(keras.layers.Dense(units=128, activation=tf.nn.relu))

# Because its one hot encoding we need to condense down to 10 units one unit for each label
# use softmax as activation function because we need probabilities for most likely label
model.add(keras.layers.Dense(units=10, activation=tf.nn.softmax))

# Adamoptimiser is considered the standard go to. start with this then experiment around

model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])

# Now I have to fit the model and feed in the data and labels i want to use
# epochs is the number of passes the training loop will make over the data
model.fit(x=x_train, y=y_train, epochs=3)

# evelaute performance of model on test data using evaluate function

loss, accuracy = model.evaluate(x_test, y_test)
print('loss = ', loss, ' accuracy = ', accuracy)