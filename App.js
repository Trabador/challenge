/**
 * Challenge, this program needs to create a local notification
 * Alexis Garcia
 */

import React, {Component} from 'react';
import {StyleSheet, Text, TextInput, View, Button, Alert} from 'react-native';
/*dependency to create local notifications */
import Notification from 'react-native-android-local-notification';

export default class App extends Component {
  constructor(){
    super();
    this.state = {
      user: '',
      message: ''
    }
  }

  componentWillMount(){
    /*listens for notification press actions */
    Notification.addListener('press', (e) => {
      switch (e.action) {
        case 'RESPOND':
          //e.payload contains the data that was created with the notification
          this.setState({ message: `Greetings ${e.payload.user}!!` })
          break;
        default:
          this.setState({message: '', user: ''})
          break;
      }
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <TextInput style={styles.input} placeholder='Insert a name'  onChangeText={(user) => this.setState({user})} />
        <Button title='Create Notification' onPress={this.handlePress}/>
        {this.state.message !== ''? <Text style={styles.message}>{this.state.message}</Text>: null} 
      </View>
    );
  }

  handlePress = () => {
    if(this.state.user !== ''){
      //this is the configuration object for the notification
      const notificationConfig = {
        subject: 'New Notification!', 
        message: 'This is a new notification created via button press',
        action: 'RESPOND',
        payload: {user: this.state.user}
      };
      Notification.create(notificationConfig);//Create notification with the config 
    } else {
      Alert.alert('Please insert a name');
    }
  }
}

/*applications styles*/
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  input: {
    marginBottom: 10
  },
  message: {
    marginTop: 10,
    fontSize: 20,
    fontWeight: 'bold'
  }
});
