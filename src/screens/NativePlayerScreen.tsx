import Video from 'react-native-video';
import React from 'react';
import {
  StyleSheet,
  View,
  Text,
  Pressable
} from 'react-native';
import { max } from 'react-native-reanimated';

export default class NativePlayerScreen extends React.Component<any, any> {
  player = null;
  constructor(props: any) {
    super(props);
    this.state = {
      index: -1
    };
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
            controls={true} />
          <View style={{marginTop:20}}>
            <Pressable
              onPress={this.turnOnCaption.bind(this)}
              style={styles.button}
            >
              <Text style={styles.text}>CC ON</Text>
            </Pressable>
          </View>  
          <View style={{marginTop:20}}>
            <Pressable
              onPress={this.turnOffCaption.bind(this)}
              style={styles.button}
            >
              <Text style={styles.text}>CC OFF</Text>
            </Pressable>
          </View>
      </View>
    );
  }
}

// Later on in your styles..
const styles = StyleSheet.create({
    text: {
      align: 'center',
      color: 'white',
      fontSize: 12,
    },
    button: {
      width: 100,
      alignItems: 'center',
      justifyContent: 'center',
      marignTop: 20,
      paddingVertical: 12,
      paddingHorizontal: 12,
      borderRadius: 4,
      elevation: 10,
      backgroundColor: 'black',
    },
    container: {
      flex: 1,
      alignItems: 'center',
    },
    backgroundVideo: {
      width: 300,
      height: 300
    },
  });