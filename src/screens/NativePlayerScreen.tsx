import Video from 'react-native-video';
import React from 'react';
import {
  StyleSheet,
  View,
  Text,
  Pressable,
  Dimensions
} from 'react-native';
import { max } from 'react-native-reanimated';
import Orientation from 'react-native-orientation-locker';

export default class NativePlayerScreen extends React.Component<any, any> {
  player = null;
  constructor(props: any) {
    super(props);
    this.state = {
      index: -1
    };
    //console.log(Dimensions.get('window').width);
  }

  
  
  turnOnCaption () {
    this.setState({
      index: 0
    });
  }
  turnOffCaption () {
    this.setState({
      index: - 1
    });
  }

  render() {
    // console.log("IN render Playertype is this.props.playerTyp : " + this.props.playerType);
    // console.log("IN render Playertype is: " + playerType);
    return (
      <View style={styles.container}>
          <Video source={{uri: "https://cdnapisec.kaltura.com/p/2503031/sp/0/playManifest/entryId/1_nu2wespu/protocol/https/format/mpegdash/video.mpd"}}   // Can be a URL or a local file.
            ref={(ref) => {
                this.player = ref
            }}
            selectedTextTrack={{
              type: "index",
              value: this.state.index,
            }}            
            style={styles.backgroundVideo}
            controls={true}
            fullscreen={false}
            paused={true}
            poster={"https://baconmockup.com/300/200/"}
            onFullscreenPlayerWillPresent={()=>{Orientation.lockToLandscape()}} />
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
      position: 'absolute',
      top: Dimensions.get('window').height/2 - 300,
      width: Dimensions.get('window').width,
      height: 415
    },
  });