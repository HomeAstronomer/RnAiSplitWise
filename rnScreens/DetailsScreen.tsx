import React, { useEffect } from 'react';
import { View, Text, Button, StyleSheet, BackHandler, Alert } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../App'; // Adjust the path as necessary

type DetailsScreenProps = NativeStackScreenProps<RootStackParamList, 'Details'>;

const DetailsScreen: React.FC<DetailsScreenProps> = ({ navigation }) => {


  return (
    <View style={styles.container}>
      <Text style={styles.text}>Details Screen </Text>
      <Button
        title="Go to TestScreen"
        onPress={() => navigation.pop()
            
        }
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  text: {
    fontSize: 24,
    marginBottom: 20,
  },
});

export default DetailsScreen;
