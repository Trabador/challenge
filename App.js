/**
 * Challenge, this program needs to create a local notification
 * Alexis Garcia
 */

import React, {Component} from 'react';
import {StyleSheet, Text, TextInput, View, Button, Alert, DeviceEventEmitter} from 'react-native';

//Custom native module to notify
import NotificationCustom from './customModules/NotifyCustom';

export default class App extends Component {
  constructor(){
    super();
    this.state = {
      user: '',
      message: ''
    }
  }

  componentWillMount(){
    //initialize notifiy channel
    NotificationCustom.createChannel();
    //Listens for reply response in notification
    this.subscription = DeviceEventEmitter.addListener('Replied', (e) => {
      this.setState({ message: e.Reply});
    });
  }

  componentWillUnmount() {
    //Removes listener for reply
    this.subscription.remove();
  }

  render() {
    return (
      <View style={styles.container}>
        <TextInput style={styles.input} placeholder='Insert a name'  onChangeText={(user) => this.setState({user})} />
        <Button title='Create Notification pruebas' onPress={this.handlePress}/>
        {this.state.message !== ''? <Text style={styles.message}>{this.state.message}</Text>: null} 
      </View>
    );
  }

  handlePress = () => {
    if(this.state.user !== ''){
      //Creates notification
      let title = `Notify by ${this.state.user}`;
      let contet = `This is a custom notification created by ${this.state.user}`;
      NotificationCustom.notifyLocal(title, contet);
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
