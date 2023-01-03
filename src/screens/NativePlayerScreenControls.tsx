import VideoPlayer from 'react-native-video-controls';
import React from 'react';
import {
  StyleSheet,
  View,
  Text
} from 'react-native';
import { max } from 'react-native-reanimated';

export default class NativePlayerScreenControls extends React.Component<any, any> {
  player = null;
  constructor(props: any) {
    super(props);
  }

  render() {
    // console.log("IN render Playertype is this.props.playerTyp : " + this.props.playerType);
    // console.log("IN render Playertype is: " + playerType);
    return (
      <View style={styles.container}>
          <VideoPlayer
              source={{ uri: "file:///storage/emulated/0/Android/data/com.example.reactnativekalturaplayer/files/dtg/clear/items/1_nu2wespu/data/local.mpd" }}
          />
      </View>
    );
  }
}

// Later on in your styles..
const styles = StyleSheet.create({
    container: {
      flex: 1,
      alignItems: 'center',
    },
    backgroundVideo: {
      width: 300,
      height: 300
    },
  });