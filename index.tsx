import { Navigation } from "react-native-navigation";
import PlayerScreen from './src/screens/PlayerScreen';
import PlayerTypeDetails from './src/screens/PlayerTypeDetails';
import PlayerTypeScreen from './src/screens/PlayerTypeScreen';
import PlayerTypeScreenTwo from './src/screens/PlayerTypeScreenTwo';
import NativePlayerScreen from './src/screens/NativePlayerScreen';
import NativePlayerScreenNew from './src/screens/NativePlayerScreenNew';
import NativePlayerScreenControls from "./src/screens/NativePlayerScreenControls";
import NativePlayerOffline from "./src/screens/NativePlayerOffline";

export const PLAYER_TYPE_SCREEN = 'com.example.reactnativekalturaplayer.PlayerTypeScreen';
export const PLAYER_TYPE_SCREEN_TWO = 'com.example.reactnativekalturaplayer.PlayerTypeScreenTwo';
export const PLAYER_TYPE_DETAILS = 'com.example.reactnativekalturaplayer.PlayerTypeDetails';
export const PLAYER_SCREEN = 'com.example.reactnativekalturaplayer.PlayerScreen';
export const NATIVE_PLAYER_SCREEN = 'com.example.reactnativekalturaplayer.NativePlayerScreen';
export const NATIVE_PLAYER_SCREEN_NEW = 'com.example.reactnativekalturaplayer.NativePlayerScreenNew';
export const NATIVE_PLAYER_SCREEN_CONTROLS = 'com.example.reactnativekalturaplayer.NativePlayerScreenControls';
export const NATIVE_PLAYER_OFFLINE = 'com.example.reactnativekalturaplayer.NativePlayerOffline';

// Registering all the screens
Navigation.registerComponent(PLAYER_TYPE_SCREEN, () => PlayerTypeScreen);
Navigation.registerComponent(PLAYER_TYPE_SCREEN_TWO, () => PlayerTypeScreenTwo);
Navigation.registerComponent(PLAYER_TYPE_DETAILS, () => PlayerTypeDetails);
Navigation.registerComponent(PLAYER_SCREEN, () => PlayerScreen);
Navigation.registerComponent(NATIVE_PLAYER_SCREEN, () => NativePlayerScreen);
Navigation.registerComponent(NATIVE_PLAYER_SCREEN_NEW, () => NativePlayerScreenNew);
Navigation.registerComponent(NATIVE_PLAYER_SCREEN_CONTROLS, () => NativePlayerScreenControls);
Navigation.registerComponent(NATIVE_PLAYER_OFFLINE, () => NativePlayerOffline);

Navigation.events().registerAppLaunchedListener(async () => {
   Navigation.setRoot({
     root: {
       stack: {
         children: [
           {
             component: {
               name: NATIVE_PLAYER_OFFLINE,
               options: {
                 topBar: {
                   visible: true,
                   title: {
                    text: 'Kaltura Player Test App'
                  }
                 },
                 statusBar: {
                   style: 'light'
                 }
               }
             }
           }
         ]
       }
     }
  });
});
