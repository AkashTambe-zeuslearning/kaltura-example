import Video from 'react-native-video';
import React from 'react';
import {
  StyleSheet,
  View,
  Text
} from 'react-native';
import { max } from 'react-native-reanimated';

export default class NativePlayerScreen extends React.Component<any, any> {
  player = null;
  constructor(props: any) {
    super(props);
  }

  render() {
    // console.log("IN render Playertype is this.props.playerTyp : " + this.props.playerType);
    // console.log("IN render Playertype is: " + playerType);
    return (
      <View style={styles.container}>
          <Video source={{uri: "file:///storage/emulated/0/Android/data/com.example.reactnativekalturaplayer/files/dtg/clear/items/1_nu2wespu/data/local.mpd"}}   // Can be a URL or a local file.
            ref={(ref) => {
                this.player = ref
            }}
            selectedTextTrack={{
              type: "index",
              value: 0,
            }}            
            style={styles.backgroundVideo}
            controls={true} />
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